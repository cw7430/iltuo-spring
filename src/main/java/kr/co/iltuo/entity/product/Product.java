package kr.co.iltuo.entity.product;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Entity
@Table(name = "`product`")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @Column(name = "`product_id`", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "`miner_category_id`", nullable = false)
    private Long minerCategoryId;

    @Column(name = "`product_code`", nullable = false, length = 65)
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
    private boolean is_recommended;

    @Column(name = "`register_date`", nullable = false)
    private LocalDateTime register_date;

    @Column(name = "`is_valid`", nullable = false)
    private boolean is_valid = true;
}
