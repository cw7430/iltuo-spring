package kr.co.iltuo.entity.order;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Entity
@Table(name = "`payment`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payment {

    @Id
    @Column(name = "`payment_id`", nullable = false)
    private Long paymentId;

    @Builder.Default
    @Column(name = "`payment_status_code`", nullable = false, length = 6)
    private String paymentStatusCode = "PS001";

    @Column(name = "`payment_method_code`", length = 6)
    private String paymentMethodCode;

    @Column(name = "`total_price`", nullable = false)
    private long totalPrice;

    @Column(name = "`delivery_price`", nullable = false)
    private long deliveryPrice;

    @Column(name = "`payment_date`")
    private Instant paymentDate;

}
