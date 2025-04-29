package kr.co.iltuo.repository.product;

import kr.co.iltuo.entity.product.MajorCategory;
import org.springframework.data.jpa.repository.*;

public interface MajorCategoryRepository extends JpaRepository<MajorCategory, Long> {

}
