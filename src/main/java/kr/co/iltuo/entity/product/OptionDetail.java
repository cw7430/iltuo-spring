package kr.co.iltuo.entity.product;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "`option_detail`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OptionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`option_detail_id`", nullable = false)
    private Long optionDetailId;

    @Column(name = "`option_id`", nullable = false)
    private Long optionId;

    @Column(name = "`option_detail_name`", nullable = false, length = 45)
    private String optionDetailName;

    @Column(name = "`option_fluctuating_price`", nullable = false)
    private int optionFluctuatingPrice;

    @Builder.Default
    @Column(name = "`is_valid`", nullable = false)
    private boolean valid = true;
}