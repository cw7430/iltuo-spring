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

    @Column(name = "`created_at`", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "`expires_at`", nullable = false)
    private LocalDateTime expiresAt;

    public void updateRefreshToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }
}
