package kr.co.iltuo.repository.product;

import kr.co.iltuo.entity.product.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ProductViewRepository extends JpaRepository<ProductView, Long> {

    List<ProductView> findByRecommendedTrue();
    List<ProductView> findByMajorCategoryId(Long majorCategoryId);
    List<ProductView> findByProductIdIn(List<Long> productIds);
}
