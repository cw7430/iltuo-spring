package kr.co.iltuo.entity.product;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.time.*;

@Entity
@Table(name = "`product_view`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Immutable
public class ProductView {
    @Id
    @Column(name = "`product_id`", nullable = false)
    private Long productId;

    @Column(name = "`major_category_id`", nullable = false)
    private Long majorCategoryId;

    @Column(name = "`miner_category_id`", nullable = false)
    private Long minerCategoryId;

    @Column(name = "`product_code`", nullable = false, length = 65, unique = true)
    private String productCode;

    @Column(name = "`product_name`", nullable = false, length = 100)
    private String productName;

    @Column(name = "`product_comments`", nullable = false, length = 100)
    private String productComments;

    @Column(name = "`price`", nullable = false)
    private long price;

    @Column(name = "`discounted_rate`", nullable = false)
    private int discountedRate;

    @Column(name = "`is_recommended`", nullable = false)
    private boolean isRecommended;

    @Column(name = "`register_date`", nullable = false)
    private LocalDateTime registerDate;

}
