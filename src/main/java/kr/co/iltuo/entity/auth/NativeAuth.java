package kr.co.iltuo.entity.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "`native_auth`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NativeAuth {
    @Id
    @Column(name = "`user_idx`", nullable = false)
    private Long userIdx;

    @Column(name = "`password`", nullable = false, length = 65)
    private String password;

    @Column(name = "`user_name`", nullable = false, length = 100)
    private String userName;

    @Column(name = "`phone_number`", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "`email`", nullable = false, length = 100)
    private String email;

    public void updateInfo(String phoneNumber, String email ) {
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
