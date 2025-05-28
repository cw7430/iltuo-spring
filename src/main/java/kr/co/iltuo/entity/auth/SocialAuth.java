package kr.co.iltuo.entity.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "`social_auth`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SocialAuth {
    @Id
    @Column(name = "`user_idx`", nullable = false)
    private Long userIdx;

    @Column(name = "`auth_provider`", nullable = false, length = 100)
    private String authProvider;

    @Column(name = "`provider_user_id`", nullable = false, length = 100)
    private String providerUserId;

    @Column(name = "`user_name`", nullable = false, length = 100)
    private String userName;

    @Column(name = "`phone_number`", length = 100)
    private String phoneNumber;

    @Column(name = "`email`", length = 100)
    private String email;
}
