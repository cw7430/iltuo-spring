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
public class SocialUserView {
    @Id
    @Column(name = "`user_idx`", nullable = false)
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
    private Instant registerDate;

    @Column(name = "`user_permissions_code`", nullable = false, length = 6)
    private String userPermissionsCode;

    @Column(name = "`auth_method_code`", nullable = false, length = 6)
    private String authMethodCode;

    @Column(name = "`auth_provider_code`", nullable = false, length = 6)
    private String authProviderCode;

    @Column(name = "`provider_user_id`", nullable = false, length = 100)
    private String providerUserId;
}
