package kr.co.iltuo.service.auth.util;

import kr.co.iltuo.dto.request.auth.AddressRequestDto;
import kr.co.iltuo.dto.request.auth.NativeSignUpRequestDto;
import kr.co.iltuo.dto.request.auth.ProfileRequestDto;
import kr.co.iltuo.dto.response.auth.AccessTokenResponseDto;
import kr.co.iltuo.dto.response.auth.RefreshTokenResponseDto;
import kr.co.iltuo.dto.response.auth.SignInResponseDto;
import kr.co.iltuo.entity.auth.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

public class AuthEntityUtil {

    public static User insertUser(String userId, String authMethod) {
        String authMethodCode = AuthConvertUtil.convertAuthMethodToCode(authMethod);
        return User.builder()
                .userId(userId)
                .authMethodCode(authMethodCode)
                .registerDate(Instant.now())
                .build();
    }

    public static void updateUserValid(User user, boolean isValid) {
        user.updateUserValid(isValid);
    }

    public static NativeAuth insertNativeAuth(
            User user, PasswordEncoder passwordEncoder, NativeSignUpRequestDto nativeSignUpRequestDto
    ) {
        return NativeAuth.builder()
                .userIdx(user.getUserIdx())
                .password(passwordEncoder.encode(nativeSignUpRequestDto.getPassword()))
                .userName(nativeSignUpRequestDto.getUserName())
                .phoneNumber(nativeSignUpRequestDto.getPhoneNumber())
                .email(nativeSignUpRequestDto.getEmail())
                .build();
    }

    public static void updateNativeAuth(
            NativeAuth nativeAuth, ProfileRequestDto profileRequestDto
    ) {
        nativeAuth.updateInfo(
                profileRequestDto.getPhoneNumber(),
                profileRequestDto.getEmail()
        );
    }

    public static SocialAuth insertSocialAuth(
            User user, String authProvider, String providerUserId, String userName, String phoneNumber, String email
    ) {
        return SocialAuth.builder()
                .userIdx(user.getUserIdx())
                .authProvider(authProvider)
                .providerUserId(providerUserId)
                .userName(userName)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
    }

    public static Address insertAddress(AddressRequestDto addressRequestDto, User user) {
        return Address.builder()
                .userIdx(user.getUserIdx())
                .postalCode(addressRequestDto.getPostalCode())
                .defaultAddress(addressRequestDto.getDefaultAddress())
                .detailAddress(addressRequestDto.getDetailAddress())
                .extraAddress(addressRequestDto.getExtraAddress())
                .main(addressRequestDto.isMain())
                .build();
    }

    public static SignInResponseDto insertSignInInfo(AccessTokenResponseDto accessTokenResponseDto, RefreshTokenResponseDto refreshTokenResponseDto, String userPermission, String authMethod) {
        return SignInResponseDto.builder()
                .accessTokenExpiresAt(accessTokenResponseDto.accessTokenExpiresAt())
                .refreshTokenExpiresAt(refreshTokenResponseDto.refreshTokenExpiresAt())
                .userPermission(userPermission)
                .authMethod(authMethod)
                .build();
    }

    public static RefreshToken insertRefreshToken(RefreshTokenResponseDto refreshTokenResponseDto, User user) {
        Instant expiresAt = Instant.ofEpochMilli(refreshTokenResponseDto.refreshTokenExpiresAt());
        return RefreshToken.builder()
                .userIdx(user.getUserIdx())
                .token(refreshTokenResponseDto.refreshToken())
                .expiresAt(expiresAt)
                .build();
    }

    public static void updateRefreshToken(RefreshTokenResponseDto refreshTokenResponseDto, RefreshToken refreshToken) {
        Instant expiresAt = Instant.ofEpochMilli(refreshTokenResponseDto.refreshTokenExpiresAt());
        refreshToken.updateRefreshToken(refreshTokenResponseDto.refreshToken(), expiresAt);
    }

}
