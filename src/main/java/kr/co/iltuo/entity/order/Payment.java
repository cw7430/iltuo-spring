package kr.co.iltuo.entity.order;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Entity
@Table(name = "`payment`")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`payment_id`", nullable = false)
    private Long paymentId;

    @Column(name = "`user_id`", nullable = false, length = 25)
    private String userId;

    @Column(name = "`payment_status_code`", nullable = false, length = 6)
    private String paymentStatusCode = "PS001";

    @Column(name = "`payment_method_code`", length = 6)
    private String paymentMethodCode;

    @Column(name = "`total_price`", nullable = false)
    private long totalPrice;

    @Column(name = "`has_delivery_price`", nullable = false)
    private boolean hasDeliveryPrice = true;

    @Column(name = "`order_date`", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "`payment_date`")
    private LocalDateTime paymentDate;

    @Column(name = "`is_valid`", nullable = false)
    private boolean isValid = true;
}
