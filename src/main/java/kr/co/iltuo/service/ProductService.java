package kr.co.iltuo.service;

import kr.co.iltuo.dto.request.product.*;
import kr.co.iltuo.dto.response.product.*;
import kr.co.iltuo.entity.product.*;

import java.util.*;

public interface ProductService {
    List<MajorCategory> getMajorCategoryList();
    List<ProductDataResponseDto> getRecommendedProductList();
    MajorCategory getMajorCategory(MajorCategoryRequestDto majorCategoryRequestDto);
    List<MinerCategory> getMinerCategoryList(MajorCategoryRequestDto majorCategoryRequestDto);
    List<ProductDataResponseDto> getProductList(MajorCategoryRequestDto majorCategoryRequestDto);
    ProductDataResponseDto getProduct(ProductRequestDto productRequestDto);
    List<Option> getOptionList(MajorCategoryRequestDto majorCategoryRequestDto);
    List<OptionView> getOptionDetailList(MajorCategoryRequestDto majorCategoryRequestDto);
}
