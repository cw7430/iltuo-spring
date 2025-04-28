package kr.co.iltuo.entity.auth;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "user_id", nullable = false, length = 25)
    private String userId;

    @Column(name = "password", nullable = false, length = 65)
    private String password;

    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "register_date", nullable = false)
    private LocalDateTime registerDate;

    @Column(name = "user_permissions_code", nullable = false, length = 6)
    private String userPermissionsCode;

    @Column(name = "is_valid", nullable = false)
    private boolean isValid;
}