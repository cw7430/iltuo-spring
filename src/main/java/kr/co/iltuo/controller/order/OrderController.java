package kr.co.iltuo.controller.order;

import jakarta.servlet.http.*;
import kr.co.iltuo.dto.request.IdxRequestDto;
import kr.co.iltuo.dto.request.order.*;
import kr.co.iltuo.dto.response.PlainResponseDto;
import kr.co.iltuo.dto.response.ResponseDto;
import kr.co.iltuo.entity.order.*;
import kr.co.iltuo.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/cart")
    public ResponseDto<List<CartView>> cartList(HttpServletRequest request) {
        return ResponseDto.success(orderService.cartList(request));
    }

    @GetMapping("/cart_options")
    public ResponseDto<List<CartOptionView>> cartOptionList(HttpServletRequest request) {
        return ResponseDto.success(orderService.cartOptionList(request));
    }

    @PostMapping("/add_cart")
    public ResponseDto<PlainResponseDto> addCart(HttpServletRequest request, @RequestBody AddCartRequestDto addCartRequestDto) {
        return ResponseDto.success(orderService.addCart(request, addCartRequestDto));
    }

    @PostMapping("/delete_cart")
    public ResponseDto<PlainResponseDto> deleteCart(HttpServletRequest request, @RequestBody IdxRequestDto idxRequestDto) {
        return ResponseDto.success(orderService.deleteCart(request, idxRequestDto));
    }

    @PostMapping("/delete_carts_all")
    public ResponseDto<PlainResponseDto> deleteCartsAll(HttpServletRequest request) {
        return ResponseDto.success(orderService.deleteCartsAll(request));
    }

}
