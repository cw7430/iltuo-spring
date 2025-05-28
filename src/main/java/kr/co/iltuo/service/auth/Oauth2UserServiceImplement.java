package kr.co.iltuo.service.auth;

import kr.co.iltuo.common.code.ResponseCode;
import kr.co.iltuo.common.exception.CustomException;
import kr.co.iltuo.entity.auth.SocialAuth;
import kr.co.iltuo.entity.auth.User;
import kr.co.iltuo.repository.auth.SocialAuthRepository;
import kr.co.iltuo.repository.auth.UserRepository;
import kr.co.iltuo.security.oauth.CustomOAuth2User;
import kr.co.iltuo.service.auth.util.AuthEntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Oauth2UserServiceImplement extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final SocialAuthRepository socialAuthRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        try {
            OAuth2User oAuth2User = super.loadUser(request);
            String registrationId = request.getClientRegistration().getRegistrationId();

            String providerUserId;
            String userId;
            String userName;
            String email;
            String phoneNumber;

            if ("naver".equals(registrationId)) {
                Map<String, Object> responseMap = (Map<String, Object>) oAuth2User.getAttributes().get("response");
                providerUserId = (String) responseMap.get("id");
                userId = "naver_" + providerUserId;
                userName = (String) responseMap.get("name");
                email = (String) responseMap.get("email");
                phoneNumber = (String) responseMap.get("mobile");
            } else if ("kakao".equals(registrationId)) {
                providerUserId = String.valueOf(oAuth2User.getAttributes().get("id"));
                userId = "kakao_" + providerUserId;
                Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                userName = (String) profile.get("nickname");
                email = null;
                phoneNumber = null;
            } else if ("google".equals(registrationId)) {
                Map<String, Object> attributes = oAuth2User.getAttributes();
                providerUserId = (String) attributes.get("sub");
                userId = "google_" + providerUserId;
                userName = (String) attributes.get("name");
                email = (String) attributes.get("email");
                phoneNumber = null;
            } else {
                providerUserId = null;
                userId = null;
                userName = null;
                email = null;
                phoneNumber = null;
            }

            Optional<User> existingUserOpt = userRepository.findByUserId(userId);

            User user = existingUserOpt.orElseGet(() -> {
                User newUser = AuthEntityUtil.insertUser(userId, "SOCIAL");
                userRepository.save(newUser);
                SocialAuth socialAuth = AuthEntityUtil.insertSocialAuth
                        (newUser, registrationId, providerUserId, userName, phoneNumber, email);
                socialAuthRepository.save(socialAuth);
                return newUser;
            });

            return new CustomOAuth2User(user);
        } catch (OAuth2AuthenticationException e) {
            throw new CustomException(ResponseCode.OAUTH2_AUTHENTICATION_FAILED);
        } catch (Exception e) {
            throw new CustomException(ResponseCode.INTERNAL_SERVER_ERROR);
        }

    }

}
