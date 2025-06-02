package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByPaymentId(Long paymentId);
}
