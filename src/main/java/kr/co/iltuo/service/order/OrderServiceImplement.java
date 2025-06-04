package kr.co.iltuo.service.order;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.iltuo.common.code.ResponseCode;
import kr.co.iltuo.common.exception.CustomException;
import kr.co.iltuo.dto.request.IdxRequestDto;
import kr.co.iltuo.dto.request.order.AddOrderRequestDto;
import kr.co.iltuo.dto.request.order.AddPaymentRequestDto;
import kr.co.iltuo.dto.response.IdxResponseDto;
import kr.co.iltuo.dto.response.PlainResponseDto;
import kr.co.iltuo.dto.response.order.CartDataResponseDto;
import kr.co.iltuo.dto.response.order.OrderGroupDataResponseDto;
import kr.co.iltuo.entity.auth.User;
import kr.co.iltuo.entity.order.*;
import kr.co.iltuo.entity.product.OptionView;
import kr.co.iltuo.entity.product.ProductView;
import kr.co.iltuo.repository.auth.UserRepository;
import kr.co.iltuo.repository.order.*;
import kr.co.iltuo.repository.product.OptionViewRepository;
import kr.co.iltuo.repository.product.ProductViewRepository;
import kr.co.iltuo.security.jwt.JwtProvider;
import kr.co.iltuo.service.order.util.OrderEntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImplement implements OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductViewRepository productViewRepository;
    private final OptionViewRepository optionViewRepository;
    private final OrderGroupRepository orderGroupRepository;
    private final OrderRepository orderRepository;
    private final OrderOptionRepository orderOptionRepository;
    private final OrderPriceRepository orderPriceRepository;
    private final CartOptionRepository cartOptionRepository;
    private final PaymentRepository paymentRepository;
    private final DeliveryRepository deliveryRepository;
    private final CartViewRepository cartViewRepository;
    private final CartOptionViewRepository cartOptionViewRepository;
    private final OrderViewRepository orderViewRepository;
    private final OrderOptionViewRepository orderOptionViewRepository;
    private final PaymentViewRepository paymentViewRepository;
    private final JwtProvider jwtProvider;

    private User getUserByToken(HttpServletRequest request) {
        String userId = jwtProvider.extractUserIdFromRequest(request, "ACCESS");
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ResponseCode.RESOURCE_NOT_FOUND));
    }

    @Override
    public List<CartDataResponseDto> cartList(HttpServletRequest request) {
        User user = getUserByToken(request);
        List<CartView> cartList = cartViewRepository.findByUserIdx(user.getUserIdx());
        List<CartOptionView> optionList = cartOptionViewRepository.findByUserIdx(user.getUserIdx());
        return OrderEntityUtil.makeCartList(cartList, optionList);
    }

    @Override
    @Transactional
    public PlainResponseDto addCart(HttpServletRequest request, AddOrderRequestDto addOrderRequestDto) {
        User user = getUserByToken(request);
        Cart cart = OrderEntityUtil.insertCart(addOrderRequestDto, user);
        cartRepository.save(cart);

        List<CartOption> cartOptions = OrderEntityUtil.insertCartOptions(addOrderRequestDto, cart);
        cartOptionRepository.saveAll(cartOptions);
        return new PlainResponseDto(true);
    }

    @Override
    @Transactional
    public PlainResponseDto deleteCart(HttpServletRequest request, IdxRequestDto idxRequestDto) {
        User user = getUserByToken(request);

        if (!cartRepository.existsByCartIdAndUserIdx(idxRequestDto.getIdx(), user.getUserIdx())) {
            throw new CustomException(ResponseCode.FORBIDDEN);
        }

        long optionDeleteCount = cartOptionRepository.deleteByCartId(idxRequestDto.getIdx());
        log.info("Deleted {} cartOption(s) for cartId: {}", optionDeleteCount, idxRequestDto.getIdx());

        cartRepository.deleteById(idxRequestDto.getIdx());

        return new PlainResponseDto(true);
    }

    @Override
    @Transactional
    public PlainResponseDto deleteCartsAll(HttpServletRequest request) {
        User user = getUserByToken(request);

        List<Cart> cartList = cartRepository.findByUserIdx(user.getUserIdx());
        List<Long> cartIds = cartList.stream().map(Cart::getCartId).toList();

        if (cartIds.isEmpty()) {
            log.info("No carts to delete for userIdx: {}", user.getUserIdx());
            return new PlainResponseDto(true);
        }

        long optionDeleteCount = cartOptionRepository.deleteAllByCartIdIn(cartIds);
        log.info("Deleted {} cartOption(s) for cartIds: {}", optionDeleteCount, cartIds);

        long cartDeleteCount = cartRepository.deleteByUserIdx(user.getUserIdx());
        log.info("Deleted {} carts for userIdx: {}", cartDeleteCount, user.getUserIdx());

        return new PlainResponseDto(true);
    }

    @Override
    public OrderGroupDataResponseDto order(HttpServletRequest request, IdxRequestDto idxRequestDto) {
        User user = getUserByToken(request);

        OrderGroup orders = orderGroupRepository.findByPaymentIdAndUserIdxAndValidTrue(idxRequestDto.getIdx(), user.getUserIdx())
                .orElseThrow(() -> new CustomException(ResponseCode.FORBIDDEN));

        List<OrderView> order = orderViewRepository.findByPaymentId(idxRequestDto.getIdx());
        if (order.isEmpty()) {
            throw new CustomException(ResponseCode.RESOURCE_NOT_FOUND);
        }

        List<OrderOptionView> orderOptions = orderOptionViewRepository.findByPaymentId(idxRequestDto.getIdx());

        return OrderEntityUtil.makeOrderGroup(orders, order, orderOptions);
    }

    @Override
    public List<OrderGroupDataResponseDto> orderGroup(HttpServletRequest request) {
        User user = getUserByToken(request);
        Sort orderDateDesc = Sort.by(Sort.Direction.DESC, "orderDate");
        List<OrderGroup> orderGroups = orderGroupRepository.findByUserIdxAndValidTrue(user.getUserIdx(), orderDateDesc);

        List<OrderView> orders = orderViewRepository.findByUserIdx(user.getUserIdx());

        return OrderEntityUtil.makeOrderGroupList(orderGroups, orders);
    }

    @Override
    @Transactional
    public IdxResponseDto addOrder(HttpServletRequest request, AddOrderRequestDto addOrderRequestDto) {
        User user = getUserByToken(request);

        OrderGroup orderGroup = OrderEntityUtil.insertOrderGroup(user);
        orderGroupRepository.save(orderGroup);

        ProductView product = productViewRepository.findById(addOrderRequestDto.getProductId())
                .orElseThrow(() -> new CustomException(ResponseCode.RESOURCE_NOT_FOUND));

        Order order = OrderEntityUtil.insertOrder(addOrderRequestDto, orderGroup, product);
        orderRepository.save(order);

        List<OrderOption> orderOptions = Collections.emptyList();

        if (!addOrderRequestDto.getOptions().isEmpty()) {
            List<Long> optionDetailIdList = addOrderRequestDto.getOptions().stream()
                    .map(IdxRequestDto::getIdx)
                    .toList();
            List<OptionView> options = optionViewRepository.findByOptionDetailIdIn(optionDetailIdList);
            orderOptions = OrderEntityUtil.insertOrderOption(addOrderRequestDto, order, product, options);
            orderOptionRepository.saveAll(orderOptions);
        }

        OrderPrice orderPrice = OrderEntityUtil.insertOrderPrice(addOrderRequestDto, order, product, orderOptions);
        orderPriceRepository.save(orderPrice);
        return new IdxResponseDto(orderGroup.getPaymentId());
    }

    @Override
    @Transactional
    public IdxResponseDto addOrders(HttpServletRequest request, List<AddOrderRequestDto> addOrderRequestList) {
        if (addOrderRequestList.isEmpty()) {
            throw new CustomException(ResponseCode.VALIDATION_ERROR);
        }
        User user = getUserByToken(request);

        OrderGroup orderGroup = OrderEntityUtil.insertOrderGroup(user);
        orderGroupRepository.save(orderGroup);

        List<Long> productIdList = addOrderRequestList.stream()
                .map(AddOrderRequestDto::getProductId)
                .toList();

        List<ProductView> productList = productViewRepository.findByProductIdIn(productIdList);

        List<Order> orderList = OrderEntityUtil.insertOrders(addOrderRequestList, orderGroup, productList);
        orderRepository.saveAll(orderList);

        List<Long> optionDetailIdList = addOrderRequestList.stream()
                .flatMap(requests -> requests.getOptions().stream())
                .map(IdxRequestDto::getIdx)
                .toList();
        List<OptionView> optionList = optionViewRepository.findByOptionDetailIdIn(optionDetailIdList);

        List<OrderOption> allOrderOptions = OrderEntityUtil.insertOrderOptions(addOrderRequestList, orderList, productList, optionList);
        orderOptionRepository.saveAll(allOrderOptions);

        List<OrderPrice> orderPriceList = OrderEntityUtil.insertOrderPrices(addOrderRequestList, orderList, productList, allOrderOptions);
        orderPriceRepository.saveAll(orderPriceList);

        return new IdxResponseDto(orderGroup.getPaymentId());
    }

    @Override
    @Transactional
    public PlainResponseDto invalidateOrder(HttpServletRequest request, IdxRequestDto idxRequestDto) {
        User user = getUserByToken(request);

        OrderGroup orderGroup = orderGroupRepository.findByPaymentIdAndUserIdxAndValidTrue(idxRequestDto.getIdx(), user.getUserIdx())
                .orElseThrow(() -> new CustomException(ResponseCode.FORBIDDEN));

        orderGroup.updateOrderValid(false);

        orderGroupRepository.save(orderGroup);

        return new PlainResponseDto(true);
    }

    @Override
    @Transactional
    public PlainResponseDto addPayment(HttpServletRequest request, AddPaymentRequestDto addPaymentRequestDto) {
        User user = getUserByToken(request);

        boolean existed = paymentRepository.existsByPaymentId(addPaymentRequestDto.getPaymentId());
        if (existed) {
            throw new CustomException(ResponseCode.FORBIDDEN);
        }

        OrderGroup orderGroup = orderGroupRepository.findByPaymentIdAndUserIdxAndValidTrue(addPaymentRequestDto.getPaymentId(), user.getUserIdx())
                .orElseThrow(() -> new CustomException(ResponseCode.FORBIDDEN));

        List<Long> orderIds = addPaymentRequestDto.getOrderIds().stream()
                .map(IdxRequestDto::getIdx)
                .toList();

        List<OrderPrice> orderPriceList = orderPriceRepository.findByOrderIdIn(orderIds);

        if (orderPriceList.isEmpty()) {
            throw new CustomException(ResponseCode.RESOURCE_NOT_FOUND);
        }

        Payment payment = OrderEntityUtil.insertPayment(addPaymentRequestDto, orderGroup, orderPriceList);
        paymentRepository.save(payment);
        orderGroupRepository.save(orderGroup);

        Delivery delivery = OrderEntityUtil.insertDelivery(addPaymentRequestDto);
        deliveryRepository.save(delivery);

        return new PlainResponseDto(true);
    }

    @Override
    public PaymentView payment(HttpServletRequest request, IdxRequestDto idxRequestDto) {
        User user = getUserByToken(request);
        return paymentViewRepository.findByPaymentIdAndUserIdx(idxRequestDto.getIdx(), user.getUserIdx())
                .orElseThrow(() -> new CustomException(ResponseCode.RESOURCE_NOT_FOUND));
    }

}
