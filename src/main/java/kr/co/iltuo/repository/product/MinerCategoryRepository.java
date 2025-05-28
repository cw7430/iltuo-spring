package kr.co.iltuo.repository.product;

import kr.co.iltuo.entity.product.MinerCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinerCategoryRepository extends JpaRepository<MinerCategory, Long> {
    List<MinerCategory> findByMajorCategoryIdAndValidTrue(Long majorCategoryId);
}