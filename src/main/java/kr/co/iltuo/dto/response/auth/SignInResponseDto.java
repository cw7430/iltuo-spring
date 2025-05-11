package kr.co.iltuo.dto.response.auth;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class SignInResponseDto {
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiresAt;
}