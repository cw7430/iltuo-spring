package kr.co.iltuo.service.auth.util;

import kr.co.iltuo.dto.response.auth.AccessTokenResponseDto;
import kr.co.iltuo.dto.response.auth.RefreshTokenResponseDto;
import kr.co.iltuo.entity.auth.User;
import kr.co.iltuo.security.jwt.JwtProvider;

public class AuthTokenUtil {
    public static AccessTokenResponseDto createAccessToken(JwtProvider jwtProvider, User user) {
        String token = jwtProvider.generateAccessToken(user);
        long expiresAt = jwtProvider.getAccessTokenExpiration(token);
        return new AccessTokenResponseDto(token, expiresAt);
    }

    public static RefreshTokenResponseDto createRefreshToken(JwtProvider jwtProvider, User user) {
        String token = jwtProvider.generateRefreshToken(user);
        long expiresAt = jwtProvider.getRefreshTokenExpiration(token);
        return new RefreshTokenResponseDto(token, expiresAt);
    }
}
