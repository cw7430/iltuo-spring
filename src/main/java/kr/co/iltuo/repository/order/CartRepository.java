package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.Cart;
import org.springframework.data.jpa.repository.*;

public interface CartRepository extends JpaRepository<Cart, Long> {

}