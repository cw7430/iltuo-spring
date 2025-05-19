package kr.co.iltuo.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.time.*;

@Entity
@Table(name = "`native_user_view`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Immutable
public class NativeUserView {
    @Id
    @Column(name = "`user_idx`")
    private Long userIdx;

    @Column(name = "`user_id`")
    private String userId;

    @Column(name = "`password`")
    private String password;

    @Column(name = "`user_name`")
    private String userName;

    @Column(name = "`phone_number`")
    private String phoneNumber;

    @Column(name = "`email`")
    private String email;

    @Column(name = "`register_date`")
    private Instant registerDate;

    @Column(name = "`user_permissions_code`")
    private String userPermissionsCode;

}
