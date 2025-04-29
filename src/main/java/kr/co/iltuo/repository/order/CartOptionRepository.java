package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.CartOption;
import org.springframework.data.jpa.repository.*;

public interface CartOptionRepository extends JpaRepository<CartOption, Long> {

}