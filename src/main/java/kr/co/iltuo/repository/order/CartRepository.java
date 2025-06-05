package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserIdx(Long userIdx);
    boolean existsByCartIdAndUserIdx(Long cartId, Long userIdx);
    long deleteByUserIdx(Long userIdx);
}