package kr.co.iltuo.dto.response.auth;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class SignInResponseDto {
    private long accessTokenExpiresAt;
    private long refreshTokenExpiresAt;
    private String userPermission;
}