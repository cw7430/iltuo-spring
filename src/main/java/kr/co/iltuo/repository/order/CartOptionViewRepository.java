package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.CartOptionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CartOptionViewRepository extends JpaRepository<CartOptionView, Long> {
    List<CartOptionView> findByUserIdx(Long userIdx);
}