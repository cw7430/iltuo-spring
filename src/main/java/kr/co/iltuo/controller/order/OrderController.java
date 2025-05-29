package kr.co.iltuo.controller.order;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.iltuo.dto.request.IdxRequestDto;
import kr.co.iltuo.dto.request.order.AddOrderRequestDto;
import kr.co.iltuo.dto.response.IdxResponseDto;
import kr.co.iltuo.dto.response.PlainResponseDto;
import kr.co.iltuo.dto.response.ResponseDto;
import kr.co.iltuo.dto.response.order.CartDataResponseDto;
import kr.co.iltuo.dto.response.order.OrderGroupDataResponseDto;
import kr.co.iltuo.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/cart")
    public ResponseDto<List<CartDataResponseDto>> cartList(HttpServletRequest request) {
        return ResponseDto.success(orderService.cartList(request));
    }

    @PostMapping("/add_cart")
    public ResponseDto<PlainResponseDto> addCart(HttpServletRequest request, @RequestBody AddOrderRequestDto addOrderRequestDto) {
        return ResponseDto.success(orderService.addCart(request, addOrderRequestDto));
    }

    @PostMapping("/delete_cart")
    public ResponseDto<PlainResponseDto> deleteCart(HttpServletRequest request, @RequestBody IdxRequestDto idxRequestDto) {
        return ResponseDto.success(orderService.deleteCart(request, idxRequestDto));
    }

    @PostMapping("/delete_carts_all")
    public ResponseDto<PlainResponseDto> deleteCartsAll(HttpServletRequest request) {
        return ResponseDto.success(orderService.deleteCartsAll(request));
    }

    @GetMapping("/order")
    public ResponseDto<OrderGroupDataResponseDto> order(HttpServletRequest request, @ModelAttribute IdxRequestDto idxRequestDto) {
        return ResponseDto.success(orderService.order(request, idxRequestDto));
    }

    @GetMapping("/order_list")
    public ResponseDto<List<OrderGroupDataResponseDto>> orderGroup(HttpServletRequest request) {
        return ResponseDto.success(orderService.orderGroup(request));
    }

    @PostMapping("/add_order")
    public ResponseDto<IdxResponseDto> addOrder(HttpServletRequest request, @RequestBody AddOrderRequestDto addOrderRequestDto) {
        return ResponseDto.success(orderService.addOrder(request, addOrderRequestDto));
    }

    @PostMapping("/add_orders")
    public ResponseDto<IdxResponseDto> addOrders(HttpServletRequest request, @RequestBody List<AddOrderRequestDto> addOrderRequestList) {
        return ResponseDto.success(orderService.addOrders(request, addOrderRequestList));
    }

}
