package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.OrderView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface OrderViewRepository extends JpaRepository<OrderView, Long> {
    List<OrderView> findByPaymentId(Long paymentId);
    List<OrderView> findByUserIdx(Long userIdx);
}
