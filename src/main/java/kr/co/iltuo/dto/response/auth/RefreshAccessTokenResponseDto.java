package kr.co.iltuo.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RefreshAccessTokenResponseDto {
    private long accessTokenExpiresAt;
    private String userPermission;
    private String authMethod;
}
