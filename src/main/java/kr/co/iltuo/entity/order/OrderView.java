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
@Table(name = "`order_view`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Immutable
public class OrderView {

    @Id
    @Column(name = "`order_id`")
    private Long orderId;

    @Column(name = "`payment_id`")
    private Long paymentId;

    @Column(name = "`user_idx`")
    private Long userIdx;

    @Column(name = "`product_name`")
    private String productName;

    @Column(name = "`product_code`")
    private String productCode;

    @Column(name = "`quantity`")
    private int quantity;

    @Column(name = "`price`")
    private long price;
}
