package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.Cart;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserIdx(Long userIdx);
    long deleteByUserIdx(Long userIdx);
}