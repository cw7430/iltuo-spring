package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.Delivery;
import org.springframework.data.jpa.repository.*;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

}
