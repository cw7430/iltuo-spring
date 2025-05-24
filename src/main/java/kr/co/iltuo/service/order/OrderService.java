package kr.co.iltuo.service.order;

import jakarta.servlet.http.*;
import kr.co.iltuo.dto.request.order.*;
import kr.co.iltuo.dto.response.PlainResponseDto;
import kr.co.iltuo.entity.order.*;

public interface OrderService {
    PlainResponseDto addCart(HttpServletRequest request, AddCartRequestDto addCartRequestDto);
    CartView cartList(HttpServletRequest request);
    CartOptionView cartOptionList(HttpServletRequest request);
}
