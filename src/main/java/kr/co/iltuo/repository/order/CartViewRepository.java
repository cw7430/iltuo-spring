package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.CartView;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(readOnly = true)
public interface CartViewRepository extends JpaRepository<CartView,Long> {
    List<CartView> findByUserIdx(Long userIdx);
}
