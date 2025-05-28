package kr.co.iltuo.repository.product;

import kr.co.iltuo.entity.product.MajorCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MajorCategoryRepository extends JpaRepository<MajorCategory, Long> {
    List<MajorCategory> findByValidTrue();
}
