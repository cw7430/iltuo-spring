package kr.co.iltuo.repository.product;

import kr.co.iltuo.dto.request.IdxRequestDto;
import kr.co.iltuo.entity.product.OptionView;
import org.springframework.data.jpa.repository.*;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface OptionViewRepository extends JpaRepository<OptionView, Long> {
    List<OptionView> findByMajorCategoryId(Long majorCategoryId);
    List<OptionView> findByOptionDetailIdIn(List<Long> optionDetailIds);
}