package kr.co.iltuo.dto.response.auth;

public record AccessTokenResponseDto(String accessToken, long accessTokenExpiresAt) {
}
