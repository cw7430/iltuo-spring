package kr.co.iltuo.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.iltuo.common.code.ResponseCode;
import kr.co.iltuo.common.exception.CustomException;
import kr.co.iltuo.entity.auth.*;
import kr.co.iltuo.repository.auth.*;
import kr.co.iltuo.security.oauth.CustomOAuth2User;
import kr.co.iltuo.service.auth.util.*;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import lombok.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class Oauth2UserServiceImplement extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final SocialAuthRepository socialAuthRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        try {
            OAuth2User oAuth2User = super.loadUser(request);
            String oauthClientName = request.getClientRegistration().getClientName();


            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));

            String providerUserId;
            String userId;
            String userName;
            String email;
            String phoneNumber;

            if ("naver".equals(oauthClientName)) {
                Map<String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
                providerUserId = responseMap.get("id").substring(0, 14);
                userId = "naver_" + providerUserId;
                userName = responseMap.get("name");
                email = responseMap.get("email");
                phoneNumber = responseMap.get("mobile");
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
                        (newUser, oauthClientName, providerUserId, userName, phoneNumber, email);
                socialAuthRepository.save(socialAuth);
                return newUser;
            });

            return new CustomOAuth2User(user);
        } catch (OAuth2AuthenticationException e) {
            throw new CustomException(ResponseCode.OAUTH2_AUTHENTICATION_FAILED);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new CustomException(ResponseCode.INTERNAL_SERVER_ERROR);
        }

    }

}
