package kr.co.iltuo.dto.response.auth;

public record RefreshAccessTokenResponseDto(long accessTokenExpiresAt, String userPermission) {
}
