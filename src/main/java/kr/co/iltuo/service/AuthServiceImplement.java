package kr.co.iltuo.service;

import jakarta.servlet.http.*;
import jakarta.transaction.Transactional;
import kr.co.iltuo.common.code.ResponseCode;
import kr.co.iltuo.common.exception.CustomException;
import kr.co.iltuo.dto.request.auth.*;
import kr.co.iltuo.entity.auth.*;
import kr.co.iltuo.provider.JwtProvider;
import kr.co.iltuo.repository.auth.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import kr.co.iltuo.dto.response.auth.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.time.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final NativeAuthRepository nativeAuthRepository;
    private final AddressRepository addressRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private static String getPermission(String userPermissionsCode) {
        return "AR002".equals(userPermissionsCode) ? "ADMIN" : "USER";
    }

    private static String getAuthMethod(String authMethodCode) {
        return switch (authMethodCode) {
            case "AM001" -> "NATIVE";
            case "AM002" -> "SOCIAL";
            case "AM003" -> "CROSS";
            default -> "GUEST";
        };
    }

    private static AccessTokenResponseDto getAccessToken(JwtProvider jwtProvider, User user) {
        String accessToken = jwtProvider.generateAccessToken(user);
        long accessTokenExpiresAt = jwtProvider.getAccessTokenExpiration(accessToken);

        return new AccessTokenResponseDto(accessToken, accessTokenExpiresAt);
    }

    private static RefreshTokenResponseDto getRefreshToken(JwtProvider jwtProvider, User user) {
        String refreshToken = jwtProvider.generateRefreshToken(user);
        long refreshTokenExpiresAt = jwtProvider.getRefreshTokenExpiration(refreshToken);

        return new RefreshTokenResponseDto(refreshToken, refreshTokenExpiresAt);
    }

    private static RefreshToken insertRefreshToken(RefreshTokenResponseDto refreshTokenResponseDto, User user) {
        Instant expiresAt = Instant.ofEpochMilli(refreshTokenResponseDto.refreshTokenExpiresAt());
        return RefreshToken.builder()
                .userIdx(user.getUserIdx())
                .token(refreshTokenResponseDto.refreshToken())
                .expiresAt(expiresAt)
                .build();
    }

    private static void updateRefreshToken(RefreshTokenResponseDto refreshTokenResponseDto, RefreshToken refreshToken) {
        Instant expiresAt = Instant.ofEpochMilli(refreshTokenResponseDto.refreshTokenExpiresAt());
        refreshToken.updateRefreshToken(refreshTokenResponseDto.refreshToken(), expiresAt);
    }

    private void upsertRefreshToken(User user, RefreshTokenResponseDto refreshTokenResponseDto) {
        RefreshToken refreshToken = refreshTokenRepository.findById(user.getUserIdx())
                .map(existingToken -> {
                    updateRefreshToken(refreshTokenResponseDto, existingToken);
                    return existingToken;
                })
                .orElseGet(() -> insertRefreshToken(refreshTokenResponseDto, user));
        refreshTokenRepository.save(refreshToken);
    }

    private static void setAccessToken(HttpServletResponse response, AccessTokenResponseDto accessTokenResponseDto) {
        Cookie cookie = new Cookie("accessToken", accessTokenResponseDto.accessToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        // cookie.setMaxAge((int)((accessTokenResponseDto.accessTokenExpiresAt() - System.currentTimeMillis()) / 1000));
        cookie.setMaxAge(-1);
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);
    }

    private void setRefreshToken(HttpServletResponse response, RefreshTokenResponseDto refreshTokenResponseDto) {
        Cookie cookie = new Cookie("refreshToken", refreshTokenResponseDto.refreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        // cookie.setMaxAge((int)((refreshTokenResponseDto.refreshTokenExpiresAt() - System.currentTimeMillis()) / 1000));
        cookie.setMaxAge(-1);
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);
    }

    private static void removeAccessToken(HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);
    }

    private void removeRefreshToken(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);
    }

    private static SignInResponseDto setSignInInfo(AccessTokenResponseDto accessTokenResponseDto, RefreshTokenResponseDto refreshTokenResponseDto, String userPermission, String authMethod) {
        return SignInResponseDto.builder()
                .accessTokenExpiresAt(accessTokenResponseDto.accessTokenExpiresAt())
                .refreshTokenExpiresAt(refreshTokenResponseDto.refreshTokenExpiresAt())
                .userPermission(userPermission)
                .authMethod(authMethod)
                .build();
    }

    private static User insertUser(NativeSignUpRequestDto nativeSignUpRequestDto) {
        return User.builder()
                .userId(nativeSignUpRequestDto.getUserId())
                .authMethodCode("AM001")
                .registerDate(Instant.now())
                .build();
    }

    private static void updateUser(User user) {
        user.updateUser(
                Instant.now()
        );
    }

    private static Address insertAddress(NativeSignUpRequestDto nativeSignUpRequestDto, User user) {
        return Address.builder()
                .userIdx(user.getUserIdx())
                .postalCode(nativeSignUpRequestDto.getPostalCode())
                .defaultAddress(nativeSignUpRequestDto.getDefaultAddress())
                .detailAddress(nativeSignUpRequestDto.getDetailAddress())
                .extraAddress(nativeSignUpRequestDto.getExtraAddress())
                .isMain(true)
                .build();
    }

    private static void updateAddress(NativeSignUpRequestDto nativeSignUpRequestDto, Address address) {
        address.updateAddress(
                nativeSignUpRequestDto.getPostalCode(),
                nativeSignUpRequestDto.getDefaultAddress(),
                nativeSignUpRequestDto.getDetailAddress(),
                nativeSignUpRequestDto.getExtraAddress(),
                true
        );
    }

    @Override
    @Transactional
    public SignInResponseDto signInNative(HttpServletResponse response, NativeSignInRequestDto nativeSignInRequestDto) {
        User user = userRepository.findByUserId(nativeSignInRequestDto.getUserId());
        if (user == null || !user.isValid()) {
            throw new CustomException(ResponseCode.LOGIN_ERROR);
        }

        NativeAuth nativeAuth = nativeAuthRepository.findById(user.getUserIdx())
                .orElseThrow(() -> new CustomException(ResponseCode.LOGIN_ERROR));

        if (!passwordEncoder.matches(nativeSignInRequestDto.getPassword(), nativeAuth.getPassword())) {
            throw new CustomException(ResponseCode.LOGIN_ERROR);
        }

        String userPermission = getPermission(user.getUserPermissionsCode());
        String authMethod = getAuthMethod(user.getAuthMethodCode());

        AccessTokenResponseDto accessTokenResponseDto = getAccessToken(jwtProvider, user);
        setAccessToken(response, accessTokenResponseDto);
        RefreshTokenResponseDto refreshTokenResponseDto = getRefreshToken(jwtProvider, user);
        setRefreshToken(response, refreshTokenResponseDto);
        upsertRefreshToken(user, refreshTokenResponseDto);

        return setSignInInfo(accessTokenResponseDto, refreshTokenResponseDto, userPermission, authMethod);
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
            user = userRepository.findByUserIdAndIsValidFalse(nativeSignUpRequestDto.getUserId());
            if (user == null) {
                throw new CustomException(ResponseCode.DUPLICATE_RESOURCE);
            }
            updateUser(user);
            user.updateUserValid(true);
            userRepository.save(user);

            NativeAuth nativeAuth = nativeAuthRepository.findById(user.getUserIdx())
                    .orElseThrow(() -> new CustomException(ResponseCode.CONFLICT));
            nativeAuth.updateInfo(
                    passwordEncoder.encode(nativeSignUpRequestDto.getPassword()),
                    nativeSignUpRequestDto.getUserName(),
                    nativeSignUpRequestDto.getPhoneNumber(),
                    nativeSignUpRequestDto.getEmail()
            );
            nativeAuthRepository.save(nativeAuth);

            Address address = addressRepository.findByUserIdxAndIsMainTrue(user.getUserIdx());
            if (address == null) {
                throw new CustomException(ResponseCode.CONFLICT);
            }
            updateAddress(nativeSignUpRequestDto, address);
            addressRepository.save(address);

        } else {
            user = insertUser(nativeSignUpRequestDto);
            userRepository.save(user);

            NativeAuth nativeAuth = NativeAuth.builder()
                    .userIdx(user.getUserIdx())
                    .password(passwordEncoder.encode(nativeSignUpRequestDto.getPassword()))
                    .userName(nativeSignUpRequestDto.getUserName())
                    .phoneNumber(nativeSignUpRequestDto.getPhoneNumber())
                    .email(nativeSignUpRequestDto.getEmail())
                    .build();
            nativeAuthRepository.save(nativeAuth);

            Address address = insertAddress(nativeSignUpRequestDto, user);
            addressRepository.save(address);
        }
        String userPermission = getPermission(user.getUserPermissionsCode());
        String authMethod = getAuthMethod(user.getAuthMethodCode());

        AccessTokenResponseDto accessTokenResponseDto = getAccessToken(jwtProvider, user);
        setAccessToken(response, accessTokenResponseDto);
        RefreshTokenResponseDto refreshTokenResponseDto = getRefreshToken(jwtProvider, user);
        setRefreshToken(response, refreshTokenResponseDto);
        upsertRefreshToken(user, refreshTokenResponseDto);

        return setSignInInfo(accessTokenResponseDto, refreshTokenResponseDto, userPermission, authMethod);
    }

    @Override
    public RefreshAccessTokenResponseDto refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtProvider.extractRefreshTokenFromCookie(request);

        if (!StringUtils.hasText(refreshToken) || !jwtProvider.validateRefreshToken(refreshToken)) {
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }

        String userId = jwtProvider.getUserIdFromRefreshToken(refreshToken);
        User user = userRepository.findByUserId(userId);
        if (user == null || !user.isValid()) {
            throw new CustomException(ResponseCode.RESOURCE_NOT_FOUND);
        }

        RefreshToken savedToken = refreshTokenRepository.findById(user.getUserIdx())
                .orElseThrow(() -> new CustomException(ResponseCode.UNAUTHORIZED));

        if (!savedToken.getToken().equals(refreshToken)) {
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }

        if (savedToken.getExpiresAt().isBefore(Instant.now())) {
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }

        AccessTokenResponseDto accessTokenResponseDto = getAccessToken(jwtProvider, user);
        setAccessToken(response, accessTokenResponseDto);

        String userPermission = getPermission(user.getUserPermissionsCode());
        String authMethod = getAuthMethod(user.getAuthMethodCode());

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
            User user = userRepository.findByUserId(userId);

            if (user != null) {
                refreshTokenRepository.deleteById(user.getUserIdx());
            }
        }

        removeAccessToken(response);
        removeRefreshToken(response);
    }

}
