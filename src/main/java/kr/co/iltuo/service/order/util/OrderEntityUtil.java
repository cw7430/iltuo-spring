package kr.co.iltuo.service.order.util;

import kr.co.iltuo.common.code.ResponseCode;
import kr.co.iltuo.common.exception.CustomException;
import kr.co.iltuo.dto.request.IdxRequestDto;
import kr.co.iltuo.dto.request.order.AddOrderRequestDto;
import kr.co.iltuo.dto.request.order.AddPaymentRequestDto;
import kr.co.iltuo.dto.response.order.*;
import kr.co.iltuo.entity.auth.User;
import kr.co.iltuo.entity.order.*;
import kr.co.iltuo.entity.product.OptionView;
import kr.co.iltuo.entity.product.ProductView;
import kr.co.iltuo.service.global.util.CalculateUtil;

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
                                .optionDetailId(option.getOptionDetailId())
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

    public static OrderGroupDataResponseDto makeOrderGroup(OrderGroup orders, List<OrderView> orderList, List<OrderOptionView> orderOptions) {
        return OrderGroupDataResponseDto.builder()
                .paymentId(orders.getPaymentId())
                .userIdx(orders.getUserIdx())
                .orderDate(orders.getOrderDate())
                .orderStatusCode(orders.getOrderStatusCode())
                .orders(
                        orderList.stream().map(order -> {
                            List<OrderOptionDataResponseDto> matchedOptions = Collections.emptyList();
                            if (!orderOptions.isEmpty()) {
                                matchedOptions = orderOptions.stream()
                                        .filter(opt -> Objects.equals(opt.getOrderId(), order.getOrderId()))
                                        .map(opt -> OrderOptionDataResponseDto.builder()
                                                .orderId(opt.getOrderId())
                                                .priorityIndex(opt.getPriorityIndex())
                                                .optionName(opt.getOptionName())
                                                .optionDetailName(opt.getOptionDetailName())
                                                .optionFluctuatingPrice(opt.getOptionFluctuatingPrice())
                                                .build())
                                        .toList();
                            }
                            return OrderDataResponseDto.builder()
                                    .orderId(order.getOrderId())
                                    .paymentId(order.getPaymentId())
                                    .productName(order.getProductName())
                                    .productCode(order.getProductCode())
                                    .quantity(order.getQuantity())
                                    .price(order.getPrice())
                                    .orderOptions(matchedOptions)
                                    .build();
                        }).toList()
                )
                .build();
    }

    public static List<OrderGroupDataResponseDto> makeOrderGroupList(List<OrderGroup> orderGroups, List<OrderView> orders) {
        return orderGroups.stream().map(group -> {
            List<OrderDataResponseDto> matchOrders = orders.stream()
                    .filter(order -> Objects.equals(order.getPaymentId(), group.getPaymentId()))
                    .map(order -> OrderDataResponseDto.builder()
                            .orderId(order.getOrderId())
                            .paymentId(order.getPaymentId())
                            .productName(order.getProductName())
                            .productCode(order.getProductCode())
                            .quantity(order.getQuantity())
                            .price(order.getPrice())
                            .orderOptions(Collections.emptyList())
                            .build()).toList();

            return OrderGroupDataResponseDto.builder()
                    .paymentId(group.getPaymentId())
                    .userIdx(group.getUserIdx())
                    .orderDate(group.getOrderDate())
                    .orderStatusCode(group.getOrderStatusCode())
                    .orders(matchOrders)
                    .build();
        }).toList();
    }

    public static OrderGroup insertOrderGroup(User user) {
        return OrderGroup.builder()
                .userIdx(user.getUserIdx())
                .orderDate(Instant.now())
                .build();
    }

    public static Order insertOrder(AddOrderRequestDto addOrderRequestDto, OrderGroup orderGroup, ProductView product) {
        return Order.builder()
                .paymentId(orderGroup.getPaymentId())
                .productName(product.getProductName())
                .productCode(product.getProductCode())
                .quantity(addOrderRequestDto.getQuantity())
                .build();
    }

    public static List<Order> insertOrders(List<AddOrderRequestDto> addOrderRequestList, OrderGroup orderGroup, List<ProductView> productList) {
        return addOrderRequestList.stream()
                .map(requestList -> {
                    ProductView matchedProduct = productList.stream()
                            .filter(p -> p.getProductId().equals(requestList.getProductId()))
                            .findFirst()
                            .orElseThrow(() -> new CustomException(ResponseCode.CONFLICT));

                    return insertOrder(requestList, orderGroup, matchedProduct);
                }).toList();
    }

    private static long getOrderPrice(AddOrderRequestDto addOrderRequestDto, ProductView product) {
        return (CalculateUtil.calculateDiscountPrice(
                product.getPrice(), product.getDiscountedRate()
        )) * addOrderRequestDto.getQuantity();
    }

    public static OrderPrice insertOrderPrice(AddOrderRequestDto addOrderRequestDto, Order order, ProductView product, List<OrderOption> orderOptions) {
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

    public static List<OrderPrice> insertOrderPrices(List<AddOrderRequestDto> addOrderRequestList, List<Order> orderList, List<ProductView> productList, List<OrderOption> orderOptions) {
        List<OrderPrice> orderPriceList = new ArrayList<>();
        for (int i = 0; i < addOrderRequestList.size(); i++) {
            AddOrderRequestDto request = addOrderRequestList.get(i);
            Order order = orderList.get(i);
            ProductView product = productList.stream()
                    .filter(p -> p.getProductId().equals(request.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(ResponseCode.CONFLICT));

            List<OrderOption> matchedOrderOptions = orderOptions.stream()
                    .filter(opt -> opt.getOrderId().equals(order.getOrderId()))
                    .toList();

            OrderPrice orderPrice = insertOrderPrice(request, order, product, matchedOrderOptions);
            orderPriceList.add(orderPrice);
        }
        return orderPriceList;
    }

    public static List<OrderOption> insertOrderOption(AddOrderRequestDto addOrderRequestDto, Order order, ProductView product, List<OptionView> options) {
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
                            .priorityIndex(option.getPriorityIndex())
                            .optionName(option.getOptionName())
                            .optionDetailName(option.getOptionDetailName())
                            .optionFluctuatingPrice(fluctuatingPrice)
                            .build();
                })
                .toList();
    }

    public static List<OrderOption> insertOrderOptions(List<AddOrderRequestDto> addOrderRequestList, List<Order> orderList, List<ProductView> productList, List<OptionView> options) {
        List<OrderOption> allOrderOptions = new ArrayList<>();
        for (int i = 0; i < addOrderRequestList.size(); i++) {
            AddOrderRequestDto request = addOrderRequestList.get(i);
            Order order = orderList.get(i);
            ProductView product = productList.stream()
                    .filter(p -> Objects.equals(p.getProductId(), request.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(ResponseCode.CONFLICT));
            List<OptionView> matchedOptions = options.stream()
                    .filter(opt -> request.getOptions().stream()
                            .map(IdxRequestDto::getIdx)
                            .toList()
                            .contains(opt.getOptionDetailId()))
                    .toList();

            List<OrderOption> orderOptions = insertOrderOption(request, order, product, matchedOptions);
            allOrderOptions.addAll(orderOptions);

        }
        return allOrderOptions;
    }

    private static long getTotalPrice(List<OrderPrice> orderPriceList) {
        return orderPriceList.stream()
                .mapToLong(OrderPrice::getPrice)
                .sum();
    }

    private static long getDeliveryPrice(long totalPrice) {
        long deliveryPrice;

        if (totalPrice >= 50000) {
            deliveryPrice = 0;
        } else if (totalPrice >= 47000) {
            deliveryPrice = 50000 - totalPrice;
        } else {
            deliveryPrice = 3000;
        }
        return deliveryPrice;
    }


    public static Payment insertPayment(AddPaymentRequestDto addPaymentRequestDto, OrderGroup orderGroup, List<OrderPrice> orderPriceList) {

        String paymentMethodCode = addPaymentRequestDto.getPaymentMethodCode();
        boolean isCard = "PM001".equals(paymentMethodCode);

        long totalPrice = getTotalPrice(orderPriceList);
        long deliveryPrice = getDeliveryPrice(totalPrice);
        String orderStatusCode = isCard ? "OS003" : "OS002";
        Instant paymentDate = isCard ? Instant.now() : null;

        orderGroup.updateOrderStatus(orderStatusCode);

        return Payment.builder()
                .paymentId(addPaymentRequestDto.getPaymentId())
                .paymentMethodCode(addPaymentRequestDto.getPaymentMethodCode())
                .totalPrice(totalPrice)
                .deliveryPrice(deliveryPrice)
                .paymentDate(paymentDate)
                .build();
    }

    public static Delivery insertDelivery(AddPaymentRequestDto addPaymentRequestDto) {
        return Delivery.builder()
                .paymentId(addPaymentRequestDto.getPaymentId())
                .postalCode(addPaymentRequestDto.getPostalCode())
                .defaultAddress(addPaymentRequestDto.getDefaultAddress())
                .detailAddress(addPaymentRequestDto.getDetailAddress())
                .extraAddress(addPaymentRequestDto.getExtraAddress())
                .build();
    }


}
