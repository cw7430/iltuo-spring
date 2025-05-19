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
    @Column(name = "`product_id`")
    private Long productId;

    @Column(name = "`major_category_id`")
    private Long majorCategoryId;

    @Column(name = "`miner_category_id`")
    private Long minerCategoryId;

    @Column(name = "`product_code`")
    private String productCode;

    @Column(name = "`product_name`")
    private String productName;

    @Column(name = "`product_comments`")
    private String productComments;

    @Column(name = "`price`")
    private long price;

    @Column(name = "`discounted_rate`")
    private int discountedRate;

    @Column(name = "`option_count`")
    private long optionCount;

    @Column(name = "`is_recommended`")
    private boolean isRecommended;

    @Column(name = "`register_date`")
    private Instant registerDate;

}
