package kr.co.iltuo.service.auth;

import jakarta.servlet.http.*;
import kr.co.iltuo.common.code.ResponseCode;
import kr.co.iltuo.common.exception.CustomException;
import kr.co.iltuo.dto.request.auth.*;
import kr.co.iltuo.entity.auth.*;
import kr.co.iltuo.security.jwt.JwtProvider;
import kr.co.iltuo.repository.auth.*;

import kr.co.iltuo.service.auth.util.*;
import kr.co.iltuo.service.global.util.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import kr.co.iltuo.dto.response.auth.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final NativeAuthRepository nativeAuthRepository;
    private final AddressRepository addressRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final NativeUserViewRepository nativeUserViewRepository;
    private final SocialUserViewRepository socialUserViewRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    private void upsertRefreshToken(User user, RefreshTokenResponseDto refreshTokenResponseDto) {
        RefreshToken refreshToken = refreshTokenRepository.findById(user.getUserIdx())
                .map(existingToken -> {
                    AuthEntityUtil.updateRefreshToken(refreshTokenResponseDto, existingToken);
                    return existingToken;
                })
                .orElseGet(() -> AuthEntityUtil.insertRefreshToken(refreshTokenResponseDto, user));
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional
    public SignInResponseDto signInNative(HttpServletResponse response, NativeSignInRequestDto nativeSignInRequestDto) {
        User user = userRepository.findByUserId(nativeSignInRequestDto.getUserId())
                .orElseThrow(() -> new CustomException(ResponseCode.LOGIN_ERROR));

        NativeAuth nativeAuth = nativeAuthRepository.findById(user.getUserIdx())
                .orElseThrow(() -> new CustomException(ResponseCode.LOGIN_ERROR));

        if (!passwordEncoder.matches(nativeSignInRequestDto.getPassword(), nativeAuth.getPassword())) {
            throw new CustomException(ResponseCode.LOGIN_ERROR);
        }

        String userPermission = AuthConvertUtil.convertPermission(user.getUserPermissionsCode());
        String authMethod = AuthConvertUtil.convertAuthMethodToString(user.getAuthMethodCode());

        AccessTokenResponseDto accessTokenResponseDto = AuthTokenUtil.createAccessToken(jwtProvider, user);
        CookieUtil.addHttpOnlyCookie(response, "accessToken", accessTokenResponseDto.accessToken(), -1);
        RefreshTokenResponseDto refreshTokenResponseDto = AuthTokenUtil.createRefreshToken(jwtProvider, user);
        CookieUtil.addHttpOnlyCookie(response, "refreshToken", refreshTokenResponseDto.refreshToken(), -1);
        upsertRefreshToken(user, refreshTokenResponseDto);

        return AuthEntityUtil.insertSignInInfo(accessTokenResponseDto, refreshTokenResponseDto, userPermission, authMethod);
    }


    @Override
    public UserIdDuplicateCheckResponseDto idDuplicateCheck(UserIdDuplicateCheckRequestDto userIdDuplicateCheckRequestDto) {
        int count = userRepository.countByUserIdAndIsValidTrue(userIdDuplicateCheckRequestDto.getUserId());
        if (count != 0) {
            throw new CustomException(ResponseCode.DUPLICATE_RESOURCE);
        }
        return new UserIdDuplicateCheckResponseDto(true);
    }

    @Override
    @Transactional
    public SignInResponseDto signUpNative(HttpServletResponse response, NativeSignUpRequestDto nativeSignUpRequestDto) {
        int userCount = userRepository.countByUserId(nativeSignUpRequestDto.getUserId());
        User user;
        if (userCount != 0) {
            user = userRepository.findByUserIdAndIsValidFalse(nativeSignUpRequestDto.getUserId())
                    .orElseThrow(() -> new CustomException(ResponseCode.DUPLICATE_RESOURCE));
            AuthEntityUtil.updateUser(user);
            AuthEntityUtil.updateUserValid(user, true);
            userRepository.save(user);

            NativeAuth nativeAuth = nativeAuthRepository.findById(user.getUserIdx())
                    .orElseThrow(() -> new CustomException(ResponseCode.CONFLICT));
            AuthEntityUtil.updateNativeAuth(nativeAuth, passwordEncoder, nativeSignUpRequestDto);
            nativeAuthRepository.save(nativeAuth);

            Address address = addressRepository.findByUserIdxAndIsMainTrue(user.getUserIdx())
                    .orElseThrow(() -> new CustomException(ResponseCode.CONFLICT));
            AuthEntityUtil.updateAddress(nativeSignUpRequestDto, address, true);
            addressRepository.save(address);

        } else {
            user = AuthEntityUtil.insertUser(nativeSignUpRequestDto.getUserId(), "NATIVE");
            userRepository.save(user);

            NativeAuth nativeAuth = AuthEntityUtil.insertNativeAuth(user, passwordEncoder, nativeSignUpRequestDto);
            nativeAuthRepository.save(nativeAuth);

            Address address = AuthEntityUtil.insertAddress(nativeSignUpRequestDto, user, true);
            addressRepository.save(address);
        }
        String userPermission = AuthConvertUtil.convertPermission(user.getUserPermissionsCode());
        String authMethod = AuthConvertUtil.convertAuthMethodToString(user.getAuthMethodCode());

        AccessTokenResponseDto accessTokenResponseDto = AuthTokenUtil.createAccessToken(jwtProvider, user);
        CookieUtil.addHttpOnlyCookie(response, "accessToken", accessTokenResponseDto.accessToken(), -1);
        RefreshTokenResponseDto refreshTokenResponseDto = AuthTokenUtil.createRefreshToken(jwtProvider, user);
        CookieUtil.addHttpOnlyCookie(response, "refreshToken", refreshTokenResponseDto.refreshToken(), -1);
        upsertRefreshToken(user, refreshTokenResponseDto);

        return AuthEntityUtil.insertSignInInfo(accessTokenResponseDto, refreshTokenResponseDto, userPermission, authMethod);
    }

    @Override
    public RefreshAccessTokenResponseDto refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtProvider.extractRefreshTokenFromCookie(request);

        if (!StringUtils.hasText(refreshToken) || !jwtProvider.validateRefreshToken(refreshToken)) {
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }

        String userId = jwtProvider.getUserIdFromRefreshToken(refreshToken);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ResponseCode.RESOURCE_NOT_FOUND));

        RefreshToken savedToken = refreshTokenRepository.findById(user.getUserIdx())
                .orElseThrow(() -> new CustomException(ResponseCode.UNAUTHORIZED));

        if (!savedToken.getToken().equals(refreshToken)) {
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }

        if (savedToken.getExpiresAt().isBefore(Instant.now())) {
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }

        AccessTokenResponseDto accessTokenResponseDto = AuthTokenUtil.createAccessToken(jwtProvider, user);
        CookieUtil.addHttpOnlyCookie(response, "accessToken", accessTokenResponseDto.accessToken(), -1);

        String userPermission = AuthConvertUtil.convertPermission(user.getUserPermissionsCode());
        String authMethod = AuthConvertUtil.convertAuthMethodToString(user.getAuthMethodCode());

        return RefreshAccessTokenResponseDto
                .builder()
                .accessTokenExpiresAt(accessTokenResponseDto.accessTokenExpiresAt())
                .userPermission(userPermission)
                .authMethod(authMethod)
                .build();
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtProvider.extractRefreshTokenFromCookie(request);

        if (StringUtils.hasText(refreshToken) && jwtProvider.validateRefreshToken(refreshToken)) {
            String userId = jwtProvider.getUserIdFromRefreshToken(refreshToken);
            User user = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new CustomException(ResponseCode.RESOURCE_NOT_FOUND));

            refreshTokenRepository.deleteById(user.getUserIdx());
        }

        CookieUtil.removeCookie(response, "accessToken");
        CookieUtil.removeCookie(response, "refreshToken");
    }

    @Override
    public NativeUserView getNativeProfile(HttpServletRequest request) {
        String token = jwtProvider.extractAccessTokenFromCookie(request);
        if (!StringUtils.hasText(token) || !jwtProvider.validateAccessToken(token)) {
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }
        String userId = jwtProvider.getUserIdFromAccessToken(token);
        return nativeUserViewRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ResponseCode.RESOURCE_NOT_FOUND));
    }

    @Override
    public SocialUserView getSocialProfile(HttpServletRequest request) {
        String token = jwtProvider.extractAccessTokenFromCookie(request);
        if (!StringUtils.hasText(token) || !jwtProvider.validateAccessToken(token)) {
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }
        String userId = jwtProvider.getUserIdFromAccessToken(token);
        return socialUserViewRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ResponseCode.RESOURCE_NOT_FOUND));
    }

}
