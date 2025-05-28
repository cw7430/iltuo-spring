package kr.co.iltuo.entity.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "`order_price`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderPrice {

    @Id
    @Column(name = "`order_id`", nullable = false)
    private Long orderId;

    @Column(name = "`price`", nullable = false)
    private long price;

}
