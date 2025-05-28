package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.CartOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartOptionRepository extends JpaRepository<CartOption, Long> {
    long deleteByCartId(Long cartId);
    long deleteAllByCartIdIn(List<Long> cartIds);
}