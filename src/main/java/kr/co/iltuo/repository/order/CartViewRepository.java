package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.CartView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CartViewRepository extends JpaRepository<CartView, Long> {
    List<CartView> findByUserIdx(Long userIdx);
}
