package kr.co.iltuo.contants.order;

import kr.co.iltuo.contants.EndPoint;
import lombok.*;

@Getter
@AllArgsConstructor
public enum OrderEndpoint {
    CART(EndPoint.BASE + "/order/cart", "장바구니", "GET"),
    CART_OPTIONS(EndPoint.BASE + "/order/cart_options", "장바구니 옵션", "GET"),
    ADD_CART(EndPoint.BASE + "/order/add_cart", "장바구니에 추가", "POST"),
    DELETE_CART(EndPoint.BASE + "/order/delete_cart", "장바구니 삭제", "POST"),
    DELETE_CARTS_ALL(EndPoint.BASE + "/order/delete_carts_all", "장바구니 일괄 삭제", "POST");

    private final String path;
    private final String description;
    private final String httpMethod;
}
