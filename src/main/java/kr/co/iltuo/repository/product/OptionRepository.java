package kr.co.iltuo.repository.product;


import kr.co.iltuo.entity.product.Option;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByMajorCategoryIdAndValidTrue(Long majorCategoryId);
}