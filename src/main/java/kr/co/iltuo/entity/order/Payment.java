package kr.co.iltuo.entity.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

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

    @Column(name = "`payment_method_code`", length = 6, nullable = false)
    private String paymentMethodCode;

    @Column(name = "`total_price`", nullable = false)
    private long totalPrice;

    @Column(name = "`delivery_price`", nullable = false)
    private long deliveryPrice;

    @Column(name = "`payment_date`")
    private Instant paymentDate;

}
