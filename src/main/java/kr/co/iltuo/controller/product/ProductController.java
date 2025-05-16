package kr.co.iltuo.controller.product;

import kr.co.iltuo.dto.request.product.*;
import kr.co.iltuo.dto.response.ResponseDto;
import kr.co.iltuo.dto.response.product.*;
import kr.co.iltuo.entity.product.*;
import kr.co.iltuo.service.product.ProductService;
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

    @GetMapping("/miner_category_list")
    public ResponseDto<List<MinerCategory>> getMinerCategoryList(@ModelAttribute ProductListRequestDto productListRequestDto) {
        return ResponseDto.success(productService.getMinerCategoryList(productListRequestDto));
    }

    @GetMapping("/product_list")
    public ResponseDto<List<ProductDataResponseDto>> getProductList(@ModelAttribute ProductListRequestDto productListRequestDto) {
        return ResponseDto.success(productService.getProductList(productListRequestDto));
    }

    @GetMapping("/product_detail")
    public ResponseDto<ProductDataResponseDto> getProduct(@ModelAttribute ProductRequestDto productRequestDto) {
        return ResponseDto.success(productService.getProduct(productRequestDto));
    }

    @GetMapping("/option_list")
    public ResponseDto<List<Option>> getOptionList(@ModelAttribute ProductListRequestDto productListRequestDto) {
        return ResponseDto.success(productService.getOptionList(productListRequestDto));
    }

    @GetMapping("/option_detail_list")
    public ResponseDto<List<OptionView>> getOptionDetailList(@ModelAttribute ProductListRequestDto productListRequestDto) {
        return ResponseDto.success(productService.getOptionDetailList(productListRequestDto));
    }
}
