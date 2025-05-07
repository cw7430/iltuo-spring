package kr.co.iltuo.repository.product;

import kr.co.iltuo.entity.product.ProductView;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface ProductViewRepository extends JpaRepository<ProductView, Long> {

    List<ProductView> findByIsRecommendedTrue();
    List<ProductView> findByMajorCategoryId(Long majorCategoryId);
}
