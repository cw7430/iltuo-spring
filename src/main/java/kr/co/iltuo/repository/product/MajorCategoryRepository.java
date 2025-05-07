package kr.co.iltuo.repository.product;

import kr.co.iltuo.entity.product.MajorCategory;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface MajorCategoryRepository extends JpaRepository<MajorCategory, Long> {
    List<MajorCategory> findByIsValidTrue();
}
