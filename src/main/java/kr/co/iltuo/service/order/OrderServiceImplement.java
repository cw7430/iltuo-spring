package kr.co.iltuo.service.order;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.iltuo.common.code.ResponseCode;
import kr.co.iltuo.common.exception.CustomException;
import kr.co.iltuo.dto.request.IdxRequestDto;
import kr.co.iltuo.dto.request.order.AddOrderRequestDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    private final CartViewRepository cartViewRepository;
    private final CartOptionViewRepository cartOptionViewRepository;
    private final OrderViewRepository orderViewRepository;
    private final OrderOptionViewRepository orderOptionViewRepository;
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
        String token = jwtProvider.extractAccessTokenFromCookie(request);
        if (!StringUtils.hasText(token) || !jwtProvider.validateAccessToken(token)) {
            throw new CustomException(ResponseCode.UNAUTHORIZED);
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
        String token = jwtProvider.extractAccessTokenFromCookie(request);
        if (!StringUtils.hasText(token) || !jwtProvider.validateAccessToken(token)) {
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }

        OrderGroup orders = orderGroupRepository.findById(idxRequestDto.getIdx())
                .orElseThrow(() -> new CustomException(ResponseCode.RESOURCE_NOT_FOUND));

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

        List<OrderGroup> orderGroups = orderGroupRepository.findByUserIdx(user.getUserIdx());

        List<OrderView> orders = orderViewRepository.findByUserIdx(user.getUserIdx());

        return List.of();
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


}
