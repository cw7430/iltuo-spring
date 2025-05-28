package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.Order;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
