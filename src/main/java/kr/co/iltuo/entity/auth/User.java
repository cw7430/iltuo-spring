package kr.co.iltuo.entity.auth;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "`user`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "`user_idx`", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;

    @Column(name = "`user_id`", unique = true, nullable = false, length = 300)
    private String userId;

    @Column(name = "`register_date`", nullable = false)
    private Instant registerDate;

    @Builder.Default
    @Column(name = "`user_permissions_code`", nullable = false, length = 6)
    private String userPermissionsCode = "AR001";

    @Column(name = "`auth_method_code`", nullable = false, length = 6)
    private String authMethodCode;

    @Builder.Default
    @Column(name = "`is_valid`", nullable = false)
    private boolean valid = true;

    public void updateUser(Instant registerDate) {
        this.registerDate = registerDate;
    }

    public void updateUserValid(boolean valid) {
        this.valid = valid;
    }
}