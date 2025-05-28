package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
