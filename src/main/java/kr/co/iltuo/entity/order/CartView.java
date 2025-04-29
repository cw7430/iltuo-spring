package kr.co.iltuo.entity.order;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "`cart_view`")
@Getter
@NoArgsConstructor
@Immutable
public class CartView {
    @Id
    @Column(name = "`cart_id`", nullable = false)
    private Long cartId;

    @Column(name = "`product_id`", nullable = false)
    private Long productId;

    @Column(name = "`product_name`", nullable = false, length = 100)
    private String productName;

    @Column(name = "`user_id`", nullable = false, length = 25)
    private String userId;

    @Column(name = "`price`", nullable = false)
    private long price;


}
