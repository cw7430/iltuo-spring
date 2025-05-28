package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.OrderView;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(readOnly = true)
public interface OrderViewRepository extends JpaRepository<OrderView, Long> {
    List<OrderView> findByPaymentId(Long paymentId);
    List<OrderView> findByUserIdx(Long userIdx);
}
