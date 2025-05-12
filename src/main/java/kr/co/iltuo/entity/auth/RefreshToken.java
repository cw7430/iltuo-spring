package kr.co.iltuo.entity.auth;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Entity
@Table(name = "`refresh_token`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefreshToken {
    @Id
    @Column(name = "`user_idx`", nullable = false)
    private Long userIdx;

    @Column(name = "`token`", nullable = false)
    private String token;

    @Column(name = "`expires_at`", nullable = false)
    private Instant expiresAt;

    public void updateRefreshToken(String token, Instant expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }
}
