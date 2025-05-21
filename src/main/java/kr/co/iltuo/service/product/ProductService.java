package kr.co.iltuo.service.product;

import kr.co.iltuo.dto.request.IdxSingleRequestDto;
import kr.co.iltuo.dto.response.product.*;
import kr.co.iltuo.entity.product.*;

import java.util.*;

public interface ProductService {
    List<MajorCategory> getMajorCategoryList();
    List<ProductDataResponseDto> getRecommendedProductList();
    List<MinerCategory> getMinerCategoryList(IdxSingleRequestDto idxSingleRequestDto);
    List<ProductDataResponseDto> getProductList(IdxSingleRequestDto idxSingleRequestDto);
    ProductDataResponseDto getProduct(IdxSingleRequestDto idxSingleRequestDto);
    List<Option> getOptionList(IdxSingleRequestDto idxSingleRequestDto);
    List<OptionView> getOptionDetailList(IdxSingleRequestDto idxSingleRequestDto);
}
