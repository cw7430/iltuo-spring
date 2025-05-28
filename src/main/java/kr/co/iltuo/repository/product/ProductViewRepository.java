package kr.co.iltuo.repository.product;

import kr.co.iltuo.entity.product.ProductView;
import org.springframework.data.jpa.repository.*;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ProductViewRepository extends JpaRepository<ProductView, Long> {

    List<ProductView> findByRecommendedTrue();
    List<ProductView> findByMajorCategoryId(Long majorCategoryId);
    List<ProductView> findByProductIdIn(List<Long> productIds);
}
