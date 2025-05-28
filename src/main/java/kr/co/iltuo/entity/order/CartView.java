package kr.co.iltuo.entity.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(name = "`product_code`", nullable = false, length = 65, unique = true)
    private String productCode;

    @Column(name = "`user_idx`")
    private Long userIdx;

    @Column(name = "`price`")
    private long price;

    @Column(name = "`discounted_rate`", nullable = false)
    private int discountedRate;

    @Column(name = "`quantity`", nullable = false)
    private int quantity;
}
