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
    @Column(name = "`cart_id`", nullable = false)
    private Long cartId;

    @Column(name = "`product_id`", nullable = false)
    private Long productId;

    @Column(name = "`product_name`", nullable = false, length = 100)
    private String productName;

    @Column(name = "`user_idx`", nullable = false)
    private Long userIdx;

    @Column(name = "`price`", nullable = false)
    private long price;


}
