package kr.co.iltuo.service.product;

import kr.co.iltuo.common.code.ResponseCode;
import kr.co.iltuo.common.exception.CustomException;
import kr.co.iltuo.dto.request.IdxSingleRequestDto;
import kr.co.iltuo.dto.response.product.*;
import kr.co.iltuo.entity.product.*;
import kr.co.iltuo.repository.product.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImplement implements ProductService {

    private final MajorCategoryRepository majorCategoryRepository;
    private final MinerCategoryRepository minerCategoryRepository;
    private final ProductViewRepository productViewRepository;
    private final OptionRepository optionRepository;
    private final OptionViewRepository optionViewRepository;

    private static Long calculateDiscountPrice(long price, int discountedRate) {
        long discountedPrice = price * (100 - discountedRate) / 100;
        if(discountedPrice % 10 != 0) {
            discountedPrice = ((discountedPrice / 10) + 1) * 10;
        }
        return discountedPrice;
    }

    private static ProductDataResponseDto makeProductData(ProductView product){
        long discountedPrice = calculateDiscountPrice(product.getPrice(), product.getDiscountedRate());
        return ProductDataResponseDto.builder()
                .productId(product.getProductId())
                .majorCategoryId(product.getMajorCategoryId())
                .minerCategoryId(product.getMinerCategoryId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .productComments(product.getProductComments())
                .price(product.getPrice())
                .discountedPrice(discountedPrice)
                .optionCount(product.getOptionCount())
                .discountedRate(product.getDiscountedRate())
                .isRecommended(product.isRecommended())
                .registerDate(product.getRegisterDate())
                .build();
    }

    private static List<ProductDataResponseDto> makeProductList(List<ProductView> productList) {
        return productList.stream()
                .map(ProductServiceImplement::makeProductData)
                .toList();
    }

    @Override
    public List<MajorCategory> getMajorCategoryList() {
        return majorCategoryRepository.findByIsValidTrue();
    }

    @Override
    public List<ProductDataResponseDto> getRecommendedProductList() {
        List<ProductView> productList = productViewRepository.findByIsRecommendedTrue();
        return makeProductList(productList);
    }

    @Override
    public List<MinerCategory> getMinerCategoryList(IdxSingleRequestDto idxSingleRequestDto) {
        return minerCategoryRepository.findByMajorCategoryIdAndIsValidTrue(idxSingleRequestDto.getIdx());
    }

    @Override
    public List<ProductDataResponseDto> getProductList(IdxSingleRequestDto idxSingleRequestDto) {
        List<ProductView> productList = productViewRepository.findByMajorCategoryId(idxSingleRequestDto.getIdx());
        return makeProductList(productList);
    }

    @Override
    public ProductDataResponseDto getProduct(IdxSingleRequestDto idxSingleRequestDto) {
        ProductView product = productViewRepository.findById(idxSingleRequestDto.getIdx())
                .orElseThrow(() -> new CustomException(ResponseCode.CONFLICT));
        return makeProductData(product);
    }

    @Override
    public List<Option> getOptionList(IdxSingleRequestDto idxSingleRequestDto) {
        return optionRepository.findByMajorCategoryIdAndIsValidTrue(idxSingleRequestDto.getIdx());
    }

    @Override
    public List<OptionView> getOptionDetailList(IdxSingleRequestDto idxSingleRequestDto) {
        return optionViewRepository.findByMajorCategoryId(idxSingleRequestDto.getIdx());
    }

}
