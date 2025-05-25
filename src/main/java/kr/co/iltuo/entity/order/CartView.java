package kr.co.iltuo.entity.order;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "`cart_view`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Immutable
public class CartView {
    @Id
    @Column(name = "`cart_id`")
    private Long cartId;

    @Column(name = "`product_id`")
    private Long productId;

    @Column(name = "`product_name`")
    private String productName;

    @Column(name = "`user_idx`")
    private Long userIdx;

    @Column(name = "`price`")
    private long price;

    @Column(name = "`quantity`", nullable = false)
    private int quantity;
}
