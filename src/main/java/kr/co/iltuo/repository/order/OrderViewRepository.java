package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.OrderView;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface OrderViewRepository extends JpaRepository<OrderView,Long> {
}
