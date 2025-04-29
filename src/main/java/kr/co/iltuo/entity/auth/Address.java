package kr.co.iltuo.entity.auth;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`address`")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @Column(name = "`address_id`", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(name = "`user_id`", nullable = false, length = 25)
    private String userId;

    @Column(name = "`postal_code`", nullable = false, length = 7)
    private String postalCode;

    @Column(name = "`default_address`", nullable = false ,columnDefinition = "TEXT")
    private String defaultAddress;

    @Column(name = "`detail_address`" ,columnDefinition = "TEXT")
    private String detailAddress;

    @Column(name = "`extra_address`" ,columnDefinition = "TEXT")
    private String extraAddress;

    @Column(name = "`is_main`", nullable = false)
    private boolean isMain;

    @Column(name = "`is_valid`", nullable = false)
    private boolean isValid = true;
}
