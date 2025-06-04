package kr.co.iltuo.service.order;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.iltuo.dto.request.IdxRequestDto;
import kr.co.iltuo.dto.request.order.AddOrderRequestDto;
import kr.co.iltuo.dto.request.order.AddPaymentRequestDto;
import kr.co.iltuo.dto.response.IdxResponseDto;
import kr.co.iltuo.dto.response.PlainResponseDto;
import kr.co.iltuo.dto.response.order.CartDataResponseDto;
import kr.co.iltuo.dto.response.order.OrderGroupDataResponseDto;
import kr.co.iltuo.entity.order.PaymentView;

import java.util.List;

public interface OrderService {
    List<CartDataResponseDto> cartList(HttpServletRequest request);
    PlainResponseDto addCart(HttpServletRequest request, AddOrderRequestDto addOrderRequestDto);
    PlainResponseDto deleteCart(HttpServletRequest request, IdxRequestDto idxRequestDto);
    PlainResponseDto deleteCartsAll(HttpServletRequest request);
    OrderGroupDataResponseDto order(HttpServletRequest request, IdxRequestDto idxRequestDto);
    List<OrderGroupDataResponseDto> orderGroup(HttpServletRequest request);
    IdxResponseDto addOrder(HttpServletRequest request, AddOrderRequestDto addOrderRequestDto);
    IdxResponseDto addOrders(HttpServletRequest request, List<AddOrderRequestDto> addOrderRequestList);
    PlainResponseDto invalidateOrder(HttpServletRequest request, IdxRequestDto idxRequestDto);
    PlainResponseDto addPayment(HttpServletRequest request, AddPaymentRequestDto addPaymentRequestDto);
    PaymentView payment(HttpServletRequest request, IdxRequestDto idxRequestDto);
}
