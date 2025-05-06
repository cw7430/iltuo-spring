package kr.co.iltuo.entity.order;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`order`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`order_id`", nullable = false)
    private Long orderId;

    @Column(name = "`product_id`", nullable = false)
    private Long productId;

    @Column(name = "`payment_id`", nullable = false)
    private Long paymentId;

    @Column(name = "`price`", nullable = false)
    private long price;

    @Column(name = "`quantity`", nullable = false)
    private int quantity;
}
