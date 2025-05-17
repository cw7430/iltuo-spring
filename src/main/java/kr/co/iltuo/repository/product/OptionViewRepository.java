package kr.co.iltuo.repository.product;

import kr.co.iltuo.entity.product.OptionView;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface OptionViewRepository extends JpaRepository<OptionView, Long> {
    List<OptionView> findByMajorCategoryId(Long majorCategoryId);
}