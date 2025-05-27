package kr.co.iltuo.service.order;

import jakarta.servlet.http.*;
import kr.co.iltuo.dto.request.IdxRequestDto;
import kr.co.iltuo.dto.request.order.*;
import kr.co.iltuo.dto.response.*;
import kr.co.iltuo.dto.response.order.*;

import java.util.*;

public interface OrderService {
    List<CartDataResponseDto> cartList(HttpServletRequest request);
    PlainResponseDto addCart(HttpServletRequest request, AddOrderRequestDto addOrderRequestDto);
    PlainResponseDto deleteCart(HttpServletRequest request, IdxRequestDto idxRequestDto);
    PlainResponseDto deleteCartsAll(HttpServletRequest request);
    IdxResponseDto addOrder(HttpServletRequest request, AddOrderRequestDto addOrderRequestDto);
    IdxResponseDto addOrders(HttpServletRequest request, List<AddOrderRequestDto> addOrderRequestDtoList);
}
