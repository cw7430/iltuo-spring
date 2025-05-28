package kr.co.iltuo.entity.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "`option_view`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Immutable
public class OptionView {
    @Id
    @Column(name = "`option_detail_id`")
    private Long optionDetailId;

    @Column(name = "`option_id`")
    private Long optionId;

    @Column(name = "`priority_index`")
    private Long priorityIndex;

    @Column(name = "`major_category_id`")
    private Long majorCategoryId;

    @Column(name = "`option_type_code`")
    private String optionTypeCode;

    @Column(name = "`option_name`")
    private String optionName;

    @Column(name = "`option_detail_name`")
    private String optionDetailName;

    @Column(name = "`option_fluctuating_price`")
    private int optionFluctuatingPrice;

}
