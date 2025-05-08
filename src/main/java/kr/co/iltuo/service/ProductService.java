package kr.co.iltuo.service;

import kr.co.iltuo.dto.request.product.*;
import kr.co.iltuo.dto.response.product.*;
import kr.co.iltuo.entity.product.*;

import java.util.*;

public interface ProductService {
    List<MajorCategory> getMajorCategoryList();
    List<ProductDataResponseDto> getRecommendedProductList();
    List<MinerCategory> getMinerCategoryList(ProductListRequestDto productListRequestDto);
    List<ProductDataResponseDto> getProductList(ProductListRequestDto productListRequestDto);
    ProductDataResponseDto getProduct(ProductRequestDto productRequestDto);
    List<Option> getOptionList(ProductListRequestDto productListRequestDto);
    List<OptionView> getOptionDetailList(ProductListRequestDto productListRequestDto);
}
