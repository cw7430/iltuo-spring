package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.OrderGroup;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface OrderGroupRepository extends JpaRepository<OrderGroup, Long> {
    List<OrderGroup> findByUserIdx(Long userIdx);
}
