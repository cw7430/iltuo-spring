package kr.co.iltuo.controller.product;

import kr.co.iltuo.dto.request.IdxRequestDto;
import kr.co.iltuo.dto.response.ResponseDto;
import kr.co.iltuo.dto.response.product.ProductDataResponseDto;
import kr.co.iltuo.entity.product.MajorCategory;
import kr.co.iltuo.entity.product.MinerCategory;
import kr.co.iltuo.entity.product.Option;
import kr.co.iltuo.entity.product.OptionView;
import kr.co.iltuo.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseDto<List<MinerCategory>> getMinerCategoryList(@ModelAttribute IdxRequestDto idxRequestDto) {
        return ResponseDto.success(productService.getMinerCategoryList(idxRequestDto));
    }

    @GetMapping("/product_list")
    public ResponseDto<List<ProductDataResponseDto>> getProductList(@ModelAttribute IdxRequestDto idxRequestDto) {
        return ResponseDto.success(productService.getProductList(idxRequestDto));
    }

    @GetMapping("/product_detail")
    public ResponseDto<ProductDataResponseDto> getProduct(@ModelAttribute IdxRequestDto idxRequestDto) {
        return ResponseDto.success(productService.getProduct(idxRequestDto));
    }

    @GetMapping("/option_list")
    public ResponseDto<List<Option>> getOptionList(@ModelAttribute IdxRequestDto idxRequestDto) {
        return ResponseDto.success(productService.getOptionList(idxRequestDto));
    }

    @GetMapping("/option_detail_list")
    public ResponseDto<List<OptionView>> getOptionDetailList(@ModelAttribute IdxRequestDto idxRequestDto) {
        return ResponseDto.success(productService.getOptionDetailList(idxRequestDto));
    }
}
