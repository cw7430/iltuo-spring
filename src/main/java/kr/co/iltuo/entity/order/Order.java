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

    @Column(name = "`payment_id`", nullable = false)
    private Long paymentId;

    @Column(name = "`product_name`", nullable = false, length = 100)
    private String productName;

    @Column(name = "`product_code`", nullable = false, length = 65)
    private String productCode;

    @Column(name = "`quantity`", nullable = false)
    private int quantity;
}
