package kr.co.iltuo.entity.product;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "`option_view`")
@Getter
@NoArgsConstructor
@Immutable
public class OptionView {
    @Id
    @Column(name = "`option_detail_id`", nullable = false)
    private Long optionDetailId;

    @Column(name = "`option_id`", nullable = false)
    private Long optionId;

    @Column(name = "`priority_index`", nullable = false)
    private Long priorityIndex;

    @Column(name = "`major_category_id`", nullable = false)
    private Long majorCategoryId;

    @Column(name = "`option_type_code`", nullable = false, length = 6)
    private String optionTypeCode;

    @Column(name = "`option_detail_name`", nullable = false, length = 45)
    private String optionDetailName;

    @Column(name = "`option_fluctuating_price`", nullable = false)
    private int optionFluctuatingPrice;

    @Column(name = "`is_valid`", nullable = false)
    private boolean isValid;
}
