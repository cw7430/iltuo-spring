package kr.co.iltuo.service;

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

    private static SignInResponseDto getAccessAndRefreshToken(AccessTokenResponseDto accessTokenResponseDto, RefreshTokenResponseDto refreshTokenResponseDto) {
        return SignInResponseDto.builder()
                .accessToken(accessTokenResponseDto.accessToken())
                .accessTokenExpiresAt(accessTokenResponseDto.accessTokenExpiresAt())
                .refreshToken(refreshTokenResponseDto.refreshToken())
                .refreshTokenExpiresAt(refreshTokenResponseDto.refreshTokenExpiresAt())
                .build();
    }

    private static User insertUser(NativeSignUpRequestDto nativeSignUpRequestDto) {
        return User.builder()
                .userId(nativeSignUpRequestDto.getUserId())
                .userName(nativeSignUpRequestDto.getUserName())
                .phoneNumber(nativeSignUpRequestDto.getPhoneNumber())
                .email(nativeSignUpRequestDto.getEmail())
                .authMethodCode("AM001")
                .registerDate(Instant.now())
                .build();
    }

    private static void updateUser(NativeSignUpRequestDto nativeSignUpRequestDto, User user) {
        user.updateInfo(
                nativeSignUpRequestDto.getUserName(),
                nativeSignUpRequestDto.getPhoneNumber(),
                nativeSignUpRequestDto.getEmail(),
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
    public SignInResponseDto signInNative(NativeSignInRequestDto nativeSignInRequestDto) {
        User user = userRepository.findByUserId(nativeSignInRequestDto.getUserId());
        if (user == null || !user.isValid()) {
            throw new CustomException(ResponseCode.LOGIN_ERROR);
        }

        NativeAuth nativeAuth = nativeAuthRepository.findById(user.getUserIdx())
                .orElseThrow(() -> new CustomException(ResponseCode.LOGIN_ERROR));

        if (!passwordEncoder.matches(nativeSignInRequestDto.getPassword(), nativeAuth.getPassword())) {
            throw new CustomException(ResponseCode.LOGIN_ERROR);
        }

        AccessTokenResponseDto accessTokenResponseDto = getAccessToken(jwtProvider,user);
        RefreshTokenResponseDto refreshTokenResponseDto = getRefreshToken(jwtProvider,user);
        upsertRefreshToken(user, refreshTokenResponseDto);

        return getAccessAndRefreshToken(accessTokenResponseDto, refreshTokenResponseDto);
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
    public SignInResponseDto signUpNative(NativeSignUpRequestDto nativeSignUpRequestDto) {
        int userCount = userRepository.countByUserId(nativeSignUpRequestDto.getUserId());
        User user;
        if (userCount != 0) {
            user = userRepository.findByUserIdAndIsValidFalse(nativeSignUpRequestDto.getUserId());
            if(user == null){
                throw new CustomException(ResponseCode.DUPLICATE_RESOURCE);
            }
            updateUser(nativeSignUpRequestDto, user);
            user.updateUserValid(true);
            userRepository.save(user);

            NativeAuth nativeAuth = nativeAuthRepository.findById(user.getUserIdx())
                    .orElseThrow(() -> new CustomException(ResponseCode.CONFLICT));
            nativeAuth.changePassword(passwordEncoder.encode(nativeSignUpRequestDto.getPassword()));
            nativeAuthRepository.save(nativeAuth);

            Address address = addressRepository.findByUserIdxAndIsMainTrue(user.getUserIdx());
            if(address == null){
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
                    .build();
            nativeAuthRepository.save(nativeAuth);

            Address address = insertAddress(nativeSignUpRequestDto, user);
            addressRepository.save(address);
        }
        AccessTokenResponseDto accessTokenResponseDto = getAccessToken(jwtProvider,user);
        RefreshTokenResponseDto refreshTokenResponseDto = getRefreshToken(jwtProvider,user);
        upsertRefreshToken(user, refreshTokenResponseDto);

        return getAccessAndRefreshToken(accessTokenResponseDto, refreshTokenResponseDto);
    }

    @Override
    public AccessTokenResponseDto refreshAccessToken(String authorizationHeader) {
        String refreshToken = jwtProvider.extractTokenFromHeader(authorizationHeader);

        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            // 401
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }

        String userId = jwtProvider.getUserIdFromRefreshToken(refreshToken);

        User user = userRepository.findByUserId(userId);
        if (user == null || !user.isValid()) {
            // 404
            throw new CustomException(ResponseCode.RESOURCE_NOT_FOUND);
        }

        // 401
        RefreshToken savedToken = refreshTokenRepository.findById(user.getUserIdx())
                .orElseThrow(() -> new CustomException(ResponseCode.UNAUTHORIZED));

        // 401
        if (!savedToken.getToken().equals(refreshToken)) {
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }

        if (savedToken.getExpiresAt().isBefore(Instant.now())) {
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }

        String newAccessToken = jwtProvider.generateAccessToken(user);
        long expiresAt = jwtProvider.getAccessTokenExpiration(newAccessToken);

        return new AccessTokenResponseDto(newAccessToken, expiresAt);

    }
}
