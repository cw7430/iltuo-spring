package kr.co.iltuo.service.order.util;

import kr.co.iltuo.dto.request.order.*;
import kr.co.iltuo.entity.auth.*;
import kr.co.iltuo.entity.order.*;

import java.time.Instant;
import java.util.*;

public class OrderEntityUtil {
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
