package kr.co.iltuo.entity.order;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "`cart_option_view`")
@Getter
@NoArgsConstructor
@Immutable
public class CartOptionView {
    @Id
    @Column(name = "`cart_option_id`", nullable = false)
    private Long cartOptionId;

    @Column(name = "`cart_id`", nullable = false)
    private Long cartId;

    @Column(name = "`option_detail_id`", nullable = false)
    private Long optionDetailId;

    @Column(name = "`option_id`", nullable = false)
    private Long optionId;

    @Column(name = "`priority_index`", nullable = false)
    private Long priorityIndex;

    @Column(name = "`option_name`", nullable = false, length = 45)
    private String optionName;

    @Column(name = "`option_type_code`", nullable = false, length = 6)
    private String optionTypeCode;

    @Column(name = "`option_detail_name`", nullable = false, length = 45)
    private String optionDetailName;

    @Column(name = "`option_fluctuating_price`", nullable = false)
    private int optionFluctuatingPrice;
}
