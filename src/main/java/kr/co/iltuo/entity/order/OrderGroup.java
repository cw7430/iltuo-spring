package kr.co.iltuo.entity.order;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "`order_group`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderGroup {

    @Id
    @Column(name = "`payment_id`", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(name = "`user_idx`", nullable = false)
    private Long userIdx;

    @Column(name = "`order_date`", nullable = false)
    private Instant orderDate;

    @Builder.Default
    @Column(name = "`order_status_code`", nullable = false)
    private String orderStatusCode = "OS001";

    @Builder.Default
    @Column(name = "`is_valid`", nullable = false)
    private boolean valid = true;


    public void updateOrderValid(boolean valid) {
        this.valid = valid;
    }

    public void updateOrderStatus(String orderStatusCode) {
        this.orderStatusCode = orderStatusCode;
    }

}
