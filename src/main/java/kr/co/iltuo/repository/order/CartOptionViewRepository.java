package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.CartOptionView;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(readOnly = true)
public interface CartOptionViewRepository extends JpaRepository<CartOptionView, Long> {
    List<CartOptionView> findByUserIdx(Long userIdx);
}