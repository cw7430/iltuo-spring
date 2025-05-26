package kr.co.iltuo.service.product.util;

import kr.co.iltuo.dto.response.product.ProductDataResponseDto;
import kr.co.iltuo.entity.product.ProductView;
import kr.co.iltuo.service.global.util.CalculateUtil;

import java.util.List;

public class ProductEntityUtil {
    public static ProductDataResponseDto makeProductData(ProductView product){
        long discountedPrice = CalculateUtil.calculateDiscountPrice(product.getPrice(), product.getDiscountedRate());
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
                .recommended(product.isRecommended())
                .registerDate(product.getRegisterDate())
                .build();
    }

    public static List<ProductDataResponseDto> makeProductList(List<ProductView> productList) {
        return productList.stream()
                .map(ProductEntityUtil::makeProductData)
                .toList();
    }
}
