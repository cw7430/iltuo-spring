package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.OrderOptionView;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(readOnly = true)
public interface OrderOptionViewRepository extends JpaRepository<OrderOptionView, Long> {
    List<OrderOptionView> findByPaymentId(Long paymentId);
}
