package kr.co.iltuo.contants.order;

import kr.co.iltuo.contants.EndPoint;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderEndpoint {
    CART(EndPoint.BASE + "/order/cart", "장바구니", "GET"),
    ADD_CART(EndPoint.BASE + "/order/add_cart", "장바구니에 추가", "POST"),
    DELETE_CART(EndPoint.BASE + "/order/delete_cart", "장바구니 삭제", "POST"),
    DELETE_CARTS_ALL(EndPoint.BASE + "/order/delete_carts_all", "장바구니 일괄 삭제", "POST"),
    ORDER(EndPoint.BASE + "/order/order", "주문 페이지", "GET"),
    ORDER_LIST(EndPoint.BASE + "/order/order_list", "주문 목록 페이지", "GET"),
    ADD_ORDER(EndPoint.BASE + "/order/add_order", "한 건 주문", "POST"),
    ADD_ORDERS(EndPoint.BASE + "/order/add_orders", "다 건 주문", "POST"),
    ADD_PAYMENT(EndPoint.BASE + "/order/add_payment", "결제", "POST"),
    DELETE_ORDER(EndPoint.BASE + "/order/delete_order", "주문 삭제", "POST"),
    PAYMENT(EndPoint.BASE + "/order/payment", "결제 정보", "GET");

    private final String path;
    private final String description;
    private final String httpMethod;
}
