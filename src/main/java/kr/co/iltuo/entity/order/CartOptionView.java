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
@Table(name = "`cart_option_view`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Immutable
public class CartOptionView {
    @Id
    @Column(name = "`cart_option_id`")
    private Long cartOptionId;

    @Column(name = "`cart_id`")
    private Long cartId;

    @Column(name = "`option_detail_id`")
    private Long optionDetailId;

    @Column(name = "`user_idx`")
    private Long userIdx;

    @Column(name = "`option_id`")
    private Long optionId;

    @Column(name = "`priority_index`")
    private Long priorityIndex;

    @Column(name = "`option_name`")
    private String optionName;

    @Column(name = "`option_type_code`")
    private String optionTypeCode;

    @Column(name = "`option_detail_name`")
    private String optionDetailName;

    @Column(name = "`option_fluctuating_price`")
    private int optionFluctuatingPrice;
}
