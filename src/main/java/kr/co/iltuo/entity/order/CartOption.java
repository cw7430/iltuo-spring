package kr.co.iltuo.entity.order;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`cart_option`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CartOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`cart_option_id`", nullable = false)
    private Long cartOptionId;

    @Column(name = "`cart_id`", nullable = false)
    private Long cartId;

    @Column(name = "`option_detail_id`", nullable = false)
    private Long optionDetailId;

}
