package kr.co.iltuo.service.product;

import kr.co.iltuo.dto.request.IdxRequestDto;
import kr.co.iltuo.dto.response.product.*;
import kr.co.iltuo.entity.product.*;

import java.util.*;

public interface ProductService {
    List<MajorCategory> getMajorCategoryList();
    List<ProductDataResponseDto> getRecommendedProductList();
    List<MinerCategory> getMinerCategoryList(IdxRequestDto idxRequestDto);
    List<ProductDataResponseDto> getProductList(IdxRequestDto idxRequestDto);
    ProductDataResponseDto getProduct(IdxRequestDto idxRequestDto);
    List<Option> getOptionList(IdxRequestDto idxRequestDto);
    List<OptionView> getOptionDetailList(IdxRequestDto idxRequestDto);
}
