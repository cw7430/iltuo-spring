package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.OrderGroup;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderGroupRepository extends JpaRepository<OrderGroup, Long> {
    Optional<OrderGroup> findByPaymentIdAndUserIdxAndValidTrue(Long paymentId, Long userIdx);
    List<OrderGroup> findByUserIdxAndValidTrue(Long userIdx, Sort sort);
}
