package kr.co.iltuo.entity.auth;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`native_auth`")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NativeAuth {
    @Id
    @Column(name = "`user_idx`", nullable = false)
    private Long userIdx;

    @Column(name = "`password`", nullable = false, length = 65)
    private String password;
}
