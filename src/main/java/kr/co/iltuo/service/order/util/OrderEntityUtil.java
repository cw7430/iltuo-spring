package kr.co.iltuo.service.order.util;

import kr.co.iltuo.dto.request.order.*;
import kr.co.iltuo.dto.response.order.*;
import kr.co.iltuo.entity.auth.*;
import kr.co.iltuo.entity.order.*;
import kr.co.iltuo.service.global.util.*;

import java.time.Instant;
import java.util.*;

public class OrderEntityUtil {

    public static List<CartDataResponseDto> makeCartList(List<CartView> cartList) {
        return cartList.stream()
                .map(cartView -> {
                    long discountedPrice = CalculateUtil.calculateDiscountPrice(cartView.getPrice(), cartView.getDiscountedRate());
                    return CartDataResponseDto.builder()
                            .cartId(cartView.getCartId())
                            .productId(cartView.getProductId())
                            .productName(cartView.getProductName())
                            .productCode(cartView.getProductCode())
                            .userIdx(cartView.getUserIdx())
                            .price(discountedPrice)
                            .quantity(cartView.getQuantity())
                            .build();
                })
                .toList();
    }


    public static Cart insertCart(AddCartRequestDto addCartRequestDto, User user) {
        return Cart.builder()
                .productId(addCartRequestDto.getProductId())
                .userIdx(user.getUserIdx())
                .quantity(addCartRequestDto.getQuantity())
                .cartDate(Instant.now())
                .build();
    }

    public static List<CartOption> insertCartOptions(AddCartRequestDto addCartRequestDto, Cart cart) {
        return addCartRequestDto.getOptions().stream()
                .map(optionDto -> CartOption.builder()
                        .cartId(cart.getCartId())
                        .optionDetailId(optionDto.getIdx())
                        .build())
                .toList();
    }
}
