package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.OrderPrice;
import org.springframework.data.jpa.repository.*;

public interface OrderPriceRepository extends JpaRepository<OrderPrice, Long> {
}
