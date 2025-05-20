package kr.co.iltuo.service.auth.util;

import kr.co.iltuo.dto.request.auth.*;
import kr.co.iltuo.dto.response.auth.*;
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

    public static void updateUser(User user) {
        user.updateUser(
                Instant.now()
        );
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
            NativeAuth nativeAuth, PasswordEncoder passwordEncoder, NativeSignUpRequestDto nativeSignUpRequestDto
    ) {
        nativeAuth.updateInfo(
                passwordEncoder.encode(nativeSignUpRequestDto.getPassword()),
                nativeSignUpRequestDto.getUserName(),
                nativeSignUpRequestDto.getPhoneNumber(),
                nativeSignUpRequestDto.getEmail()
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
                .isMain(addressRequestDto.isMain())
                .build();
    }

    public static void updateAddress(AddressRequestDto addressRequestDto, Address address, boolean isMain) {
        address.updateAddress(
                addressRequestDto.getPostalCode(),
                addressRequestDto.getDefaultAddress(),
                addressRequestDto.getDetailAddress(),
                addressRequestDto.getExtraAddress(),
                isMain
        );
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
