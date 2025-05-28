package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.OrderOptionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface OrderOptionViewRepository extends JpaRepository<OrderOptionView, Long> {
    List<OrderOptionView> findByPaymentId(Long paymentId);
}
