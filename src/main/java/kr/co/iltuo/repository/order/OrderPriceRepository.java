package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.OrderPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPriceRepository extends JpaRepository<OrderPrice, Long> {
}
