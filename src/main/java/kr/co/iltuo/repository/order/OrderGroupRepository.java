package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.OrderGroup;
import org.springframework.data.jpa.repository.*;

public interface OrderGroupRepository extends JpaRepository<OrderGroup, Long> {
}
