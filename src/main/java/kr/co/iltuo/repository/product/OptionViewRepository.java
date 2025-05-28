package kr.co.iltuo.repository.product;

import kr.co.iltuo.entity.product.OptionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface OptionViewRepository extends JpaRepository<OptionView, Long> {
    List<OptionView> findByMajorCategoryId(Long majorCategoryId);
    List<OptionView> findByOptionDetailIdIn(List<Long> optionDetailIds);
}