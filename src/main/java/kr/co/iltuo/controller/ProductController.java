package kr.co.iltuo.controller;

import kr.co.iltuo.dto.request.product.*;
import kr.co.iltuo.dto.response.ResponseDto;
import kr.co.iltuo.dto.response.product.*;
import kr.co.iltuo.entity.product.*;
import kr.co.iltuo.service.ProductService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/major_category_list")
    public ResponseDto<List<MajorCategory>> getMajorCategoryList() {
        return ResponseDto.success(productService.getMajorCategoryList());
    }

    @GetMapping("/recommended_product_list")
    public ResponseDto<List<ProductDataResponseDto>> getRecommendedProductList() {
        return ResponseDto.success(productService.getRecommendedProductList());
    }

    @GetMapping("/major_category")
    public ResponseDto<MajorCategory> getMajorCategory(MajorCategoryRequestDto majorCategoryRequestDto) {
        return ResponseDto.success(productService.getMajorCategory(majorCategoryRequestDto));
    }

    @GetMapping("/miner_category_list")
    public ResponseDto<List<MinerCategory>> getMinerCategoryList(MajorCategoryRequestDto majorCategoryRequestDto) {
        return ResponseDto.success(productService.getMinerCategoryList(majorCategoryRequestDto));
    }

    @GetMapping("/product_list")
    public ResponseDto<List<ProductDataResponseDto>> getProductList(MajorCategoryRequestDto majorCategoryRequestDto) {
        return ResponseDto.success(productService.getProductList(majorCategoryRequestDto));
    }

    @GetMapping("/product_detail")
    public ResponseDto<ProductDataResponseDto> getProduct(ProductRequestDto productRequestDto) {
        return ResponseDto.success(productService.getProduct(productRequestDto));
    }

    @GetMapping("/option_list")
    public ResponseDto<List<Option>> getOptionList(MajorCategoryRequestDto majorCategoryRequestDto) {
        return ResponseDto.success(productService.getOptionList(majorCategoryRequestDto));
    }

    @GetMapping("/option_detail_list")
    public ResponseDto<List<OptionView>> getOptionDetailList(MajorCategoryRequestDto majorCategoryRequestDto) {
        return ResponseDto.success(productService.getOptionDetailList(majorCategoryRequestDto));
    }
}
