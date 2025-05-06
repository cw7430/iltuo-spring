package kr.co.iltuo.entity.order;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "`order_view`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Immutable
public class OrderView {
    @Id
    @Column(name = "`order_id`", nullable = false)
    private Long orderId;

    @Column(name = "`product_id`", nullable = false)
    private Long productId;

    @Column(name = "`product_name`", nullable = false, length = 100)
    private String productName;

    @Column(name = "`payment_id`", nullable = false)
    private Long paymentId;

    @Column(name = "`price`", nullable = false)
    private long price;

    @Column(name = "`quantity`", nullable = false)
    private int quantity;
}
