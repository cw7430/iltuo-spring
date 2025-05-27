package kr.co.iltuo.service.order.util;

import kr.co.iltuo.dto.request.order.*;
import kr.co.iltuo.dto.response.order.*;
import kr.co.iltuo.entity.auth.*;
import kr.co.iltuo.entity.order.*;
import kr.co.iltuo.entity.product.*;
import kr.co.iltuo.service.global.util.*;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class OrderEntityUtil {

    public static List<CartDataResponseDto> makeCartList(List<CartView> cartList, List<CartOptionView> optionList) {
        return cartList.stream()
                .map(cart -> {
                    long discountedPrice = CalculateUtil.calculateDiscountPrice(cart.getPrice(), cart.getDiscountedRate());

                    List<CartOptionView> matchedOptions = optionList.stream()
                            .filter(option -> Objects.equals(option.getCartId(), cart.getCartId()))
                            .sorted(Comparator.comparing(CartOptionView::getPriorityIndex))
                            .toList();

                    long totalPrice = discountedPrice * cart.getQuantity();
                    List<CartOptionDataResponseDto> options = new ArrayList<>();

                    for (CartOptionView option : matchedOptions) {
                        long optionPrice = CalculateUtil.calculateOptionPrice(
                                totalPrice,
                                option.getOptionFluctuatingPrice(),
                                option.getOptionTypeCode()
                        );
                        totalPrice += optionPrice;

                        options.add(CartOptionDataResponseDto.builder()
                                .cartId(option.getCartId())
                                .priorityIndex(option.getPriorityIndex())
                                .optionName(option.getOptionName())
                                .optionDetailName(option.getOptionDetailName())
                                .optionPrice(optionPrice)
                                .build());
                    }

                    return CartDataResponseDto.builder()
                            .cartId(cart.getCartId())
                            .productId(cart.getProductId())
                            .productName(cart.getProductName())
                            .productCode(cart.getProductCode())
                            .userIdx(cart.getUserIdx())
                            .price(totalPrice)
                            .quantity(cart.getQuantity())
                            .options(options)
                            .build();
                })
                .toList();
    }


    public static Cart insertCart(AddOrderRequestDto addOrderRequestDto, User user) {
        return Cart.builder()
                .productId(addOrderRequestDto.getProductId())
                .userIdx(user.getUserIdx())
                .quantity(addOrderRequestDto.getQuantity())
                .cartDate(Instant.now())
                .build();
    }

    public static List<CartOption> insertCartOptions(AddOrderRequestDto addOrderRequestDto, Cart cart) {
        return addOrderRequestDto.getOptions().stream()
                .map(option -> CartOption.builder()
                        .cartId(cart.getCartId())
                        .optionDetailId(option.getIdx())
                        .build())
                .toList();
    }

    public static OrderGroup insertOrderGroup(User user) {
        return OrderGroup.builder()
                .userIdx(user.getUserIdx())
                .orderDate(Instant.now())
                .build();
    }

    public static Order insertOrder(AddOrderRequestDto addOrderRequestDto, OrderGroup orderGroup, Product product) {
        return Order.builder()
                .paymentId(orderGroup.getPaymentId())
                .productName(product.getProductName())
                .quantity(addOrderRequestDto.getQuantity())
                .build();
    }

    private static long getOrderPrice(AddOrderRequestDto addOrderRequestDto, Product product) {
        return (CalculateUtil.calculateDiscountPrice(
                product.getPrice(), product.getDiscountedRate()
        )) * addOrderRequestDto.getQuantity();
    }

    public static OrderPrice insertOrderPrice(AddOrderRequestDto addOrderRequestDto, Order order, Product product, List<OrderOption> orderOptions) {
        long price = getOrderPrice(addOrderRequestDto, product);

        if (!orderOptions.isEmpty()) {
            long optionTotal = orderOptions.stream()
                    .mapToLong(OrderOption::getOptionFluctuatingPrice)
                    .sum();
            price += optionTotal;
        }

        return OrderPrice.builder()
                .orderId(order.getOrderId())
                .price(price)
                .build();
    }

    public static List<OrderOption> insertOrderOption(AddOrderRequestDto addOrderRequestDto, Order order, Product product, List<OptionView> options) {
        long basePrice = getOrderPrice(addOrderRequestDto, product);
        AtomicLong currentPrice = new AtomicLong(basePrice);

        return options.stream()
                .sorted(Comparator.comparing(OptionView::getPriorityIndex))
                .map(option -> {
                    long fluctuatingPrice = CalculateUtil.calculateOptionPrice(
                            currentPrice.get(),
                            option.getOptionFluctuatingPrice(),
                            option.getOptionTypeCode()
                    );
                    currentPrice.addAndGet(fluctuatingPrice);

                    return OrderOption.builder()
                            .orderId(order.getOrderId())
                            .optionName(option.getOptionName())
                            .optionDetailName(option.getOptionDetailName())
                            .optionFluctuatingPrice(fluctuatingPrice)
                            .build();
                })
                .toList();
    }


}
