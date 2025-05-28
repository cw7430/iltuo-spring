package kr.co.iltuo.entity.order;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "`cart`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`cart_id`", nullable = false)
    private Long cartId;

    @Column(name = "`product_id`", nullable = false)
    private Long productId;

    @Column(name = "`user_idx`", nullable = false)
    private Long userIdx;

    @Column(name = "`quantity`", nullable = false)
    private int quantity;

    @Column(name = "`cart_date`", nullable = false)
    private Instant cartDate;
}
