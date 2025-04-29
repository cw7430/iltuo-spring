package kr.co.iltuo.entity.order;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name = "`cart`")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`cart_id`", nullable = false)
    private Long cartId;

    @Column(name = "`product_id`", nullable = false)
    private Long productId;

    @Column(name = "`user_id`", nullable = false, length = 25)
    private String userId;

    @Column(name = "`quantity`", nullable = false)
    private int quantity;

    @Column(name = "`cart_date`", nullable = false)
    private LocalDateTime cartDate;
}
