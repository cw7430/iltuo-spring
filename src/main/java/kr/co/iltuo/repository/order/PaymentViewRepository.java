package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.PaymentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface PaymentViewRepository extends JpaRepository<PaymentView, Long> {
    Optional<PaymentView> findByPaymentIdAndUserIdx(Long paymentId, Long userIdx);
}
