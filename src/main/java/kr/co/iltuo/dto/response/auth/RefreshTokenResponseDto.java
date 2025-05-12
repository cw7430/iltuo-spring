package kr.co.iltuo.dto.response.auth;

public record RefreshTokenResponseDto(String refreshToken, long refreshTokenExpiresAt) {
}
