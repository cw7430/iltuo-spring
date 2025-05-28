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
@Table(name = "`order_option_view`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Immutable
public class OrderOptionView {
    @Id
    @Column(name = "`order_option_id`")
    private Long orderOptionId;

    @Column(name = "`order_id`")
    private Long orderId;

    @Column(name = "`payment_id`")
    private Long paymentId;

    @Column(name = "`priority_index`")
    private Long priorityIndex;

    @Column(name = "`option_name`")
    private String optionName;

    @Column(name = "`option_detail_name`")
    private String optionDetailName;

    @Column(name = "`option_fluctuating_price`")
    private long optionFluctuatingPrice;
}
