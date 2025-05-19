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
    @Column(name = "`order_id`")
    private Long orderId;

    @Column(name = "`product_id`")
    private Long productId;

    @Column(name = "`product_name`")
    private String productName;

    @Column(name = "`payment_id`")
    private Long paymentId;

    @Column(name = "`price`")
    private long price;

    @Column(name = "`quantity`")
    private int quantity;
}
