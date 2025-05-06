package kr.co.iltuo.entity.auth;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`social_auth`")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SocialAuth {
    @Id
    @Column(name = "`user_idx`", nullable = false)
    private Long userIdx;

    @Column(name = "`auth_provider_code`", nullable = false, length = 6)
    private String authProviderCode;

    @Column(name = "`provider_user_id`", nullable = false, length = 100)
    private String providerUserId;
}
