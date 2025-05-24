package kr.co.iltuo.service.order;

import jakarta.servlet.http.*;
import kr.co.iltuo.dto.request.IdxRequestDto;
import kr.co.iltuo.dto.request.order.*;
import kr.co.iltuo.dto.response.PlainResponseDto;
import kr.co.iltuo.entity.order.*;

import java.util.*;

public interface OrderService {
    PlainResponseDto addCart(HttpServletRequest request, AddCartRequestDto addCartRequestDto);
    List<CartView> cartList(HttpServletRequest request);
    List<CartOptionView> cartOptionList(HttpServletRequest request);
    PlainResponseDto deleteCartSingle(HttpServletRequest request, IdxRequestDto idxRequestDto);
}
