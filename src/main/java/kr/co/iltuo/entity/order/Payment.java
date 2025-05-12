package kr.co.iltuo.entity.order;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Entity
@Table(name = "`payment`")
@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`payment_id`", nullable = false)
    private Long paymentId;

    @Column(name = "`user_idx`", nullable = false)
    private Long userIdx;

    @Builder.Default
    @Column(name = "`payment_status_code`", nullable = false, length = 6)
    private String paymentStatusCode = "PS001";

    @Column(name = "`payment_method_code`", length = 6)
    private String paymentMethodCode;

    @Column(name = "`total_price`", nullable = false)
    private long totalPrice;

    @Builder.Default
    @Column(name = "`has_delivery_price`", nullable = false)
    private boolean hasDeliveryPrice = true;

    @Column(name = "`order_date`", nullable = false)
    private Instant orderDate;

    @Column(name = "`payment_date`")
    private Instant paymentDate;

    @Builder.Default
    @Column(name = "`is_valid`", nullable = false)
    private boolean isValid = true;
}
