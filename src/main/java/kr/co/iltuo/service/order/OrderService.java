package kr.co.iltuo.service.order;

import jakarta.servlet.http.*;
import kr.co.iltuo.dto.request.IdxRequestDto;
import kr.co.iltuo.dto.request.order.*;
import kr.co.iltuo.dto.response.PlainResponseDto;
import kr.co.iltuo.entity.order.*;

import java.util.*;

public interface OrderService {
    List<CartView> cartList(HttpServletRequest request);
    List<CartOptionView> cartOptionList(HttpServletRequest request);
    PlainResponseDto addCart(HttpServletRequest request, AddCartRequestDto addCartRequestDto);
    PlainResponseDto deleteCart(HttpServletRequest request, IdxRequestDto idxRequestDto);
    PlainResponseDto deleteCartsAll(HttpServletRequest request);
}
