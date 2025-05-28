package kr.co.iltuo.entity.order;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`order_option`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`order_option_id`", nullable = false)
    private Long orderOptionId;

    @Column(name = "`order_id`", nullable = false)
    private Long orderId;

    @Column(name = "`priority_index`", nullable = false)
    private Long priorityIndex;

    @Column(name = "`option_name`", nullable = false, length = 45)
    private String optionName;

    @Column(name = "`option_detail_name`", nullable = false, length = 45)
    private String optionDetailName;

    @Column(name = "`option_fluctuating_price`", nullable = false)
    private long optionFluctuatingPrice;
}
