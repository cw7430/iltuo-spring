package kr.co.iltuo.repository.order;

import kr.co.iltuo.entity.order.OrderOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderOptionRepository extends JpaRepository<OrderOption,Long> {
}
