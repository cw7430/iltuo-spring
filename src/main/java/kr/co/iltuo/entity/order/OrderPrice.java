package kr.co.iltuo.entity.order;

import jakarta.persistence.*;
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
