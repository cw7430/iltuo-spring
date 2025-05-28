package kr.co.iltuo.contants.product;

import kr.co.iltuo.contants.EndPoint;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductEndpoint {
    MAJOR_CATEGORY_LIST(EndPoint.BASE + "/product/major_category_list", "주 목차 리스트", "GET"),
    RECOMMENDED_PRODUCT_LIST(EndPoint.BASE + "/product/recommended_product_list", "추천 상품 리스트", "GET"),
    MAJOR_CATEGORY(EndPoint.BASE + "/product/major_category", "주 목차", "GET"),
    MINER_CATEGORY_LIST(EndPoint.BASE + "/product/miner_category_list", "보조 목차 리스트", "GET"),
    PRODUCT_LIST(EndPoint.BASE + "/product/product_list", "상품 리스트", "GET"),
    PRODUCT_DETAIL(EndPoint.BASE + "/product/product_detail", "상품 상세", "GET"),
    OPTION_LIST(EndPoint.BASE + "/product/option_list", "옵션 리스트", "GET"),
    OPTION_DETAIL_LIST(EndPoint.BASE + "/product/option_detail_list", "상세 옵션 리스트", "GET");

    private final String path;
    private final String description;
    private final String httpMethod;
}
