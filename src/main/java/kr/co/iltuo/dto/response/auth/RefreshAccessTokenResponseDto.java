package kr.co.iltuo.dto.response.auth;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class RefreshAccessTokenResponseDto {
    private long accessTokenExpiresAt;
    private String userPermission;
    private String authMethod;
}
