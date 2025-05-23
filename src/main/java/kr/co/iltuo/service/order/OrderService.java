package kr.co.iltuo.service.order;

import jakarta.servlet.http.*;
import kr.co.iltuo.dto.request.order.*;
import kr.co.iltuo.dto.response.PlainResponseDto;

public interface OrderService {
    PlainResponseDto addCart(HttpServletRequest request, AddCartRequestDto addCartRequestDto);
}
