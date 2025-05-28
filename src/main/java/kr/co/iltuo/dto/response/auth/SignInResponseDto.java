package kr.co.iltuo.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SignInResponseDto {
    private long accessTokenExpiresAt;
    private long refreshTokenExpiresAt;
    private String userPermission;
    private String authMethod;
}