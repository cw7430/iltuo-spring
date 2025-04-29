package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.CartView;
import org.springframework.data.jpa.repository.*;

public interface CartViewRepository extends JpaRepository<CartView,Long> {
}
