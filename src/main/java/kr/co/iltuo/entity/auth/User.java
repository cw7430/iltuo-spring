package kr.co.iltuo.entity.auth;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

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

    @Column(name = "`user_id`", unique = true, nullable = false, length = 25)
    private String userId;

    @Column(name = "`user_name`", nullable = false, length = 100)
    private String userName;

    @Column(name = "`phone_number`", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "`email`", nullable = false, length = 100)
    private String email;

    @Column(name = "`register_date`", nullable = false)
    private LocalDateTime registerDate;

    @Builder.Default
    @Column(name = "`user_permissions_code`", nullable = false, length = 6)
    private String userPermissionsCode = "AR001";

    @Column(name = "`auth_method_code`", nullable = false, length = 6)
    private String authMethodCode;

    @Builder.Default
    @Column(name = "`is_valid`", nullable = false)
    private boolean isValid = true;

    public void updateInfo(String userName, String phoneNumber, String email, LocalDateTime registerDate) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.registerDate = registerDate;
    }

    public void updateUserValid(boolean isValid) {
        this.isValid = isValid;
    }
}