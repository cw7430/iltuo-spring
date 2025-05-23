package kr.co.iltuo.service.order;

import jakarta.servlet.http.*;
import kr.co.iltuo.common.code.ResponseCode;
import kr.co.iltuo.common.exception.CustomException;
import kr.co.iltuo.dto.request.order.*;
import kr.co.iltuo.dto.response.PlainResponseDto;
import kr.co.iltuo.entity.auth.*;
import kr.co.iltuo.entity.order.*;
import kr.co.iltuo.repository.auth.*;
import kr.co.iltuo.repository.order.*;
import kr.co.iltuo.security.jwt.JwtProvider;
import kr.co.iltuo.service.order.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImplement implements OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartOptionRepository cartOptionRepository;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public PlainResponseDto addCart(HttpServletRequest request, AddCartRequestDto addCartRequestDto) {
        String userId = jwtProvider.extractUserIdFromRequest(request, "ACCESS");
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ResponseCode.RESOURCE_NOT_FOUND));
        Cart cart = OrderEntityUtil.insertCart(addCartRequestDto, user);
        cartRepository.save(cart);

        List<CartOption> cartOptions = OrderEntityUtil.insertCartOptions(addCartRequestDto, cart);
        cartOptionRepository.saveAll(cartOptions);
        return new PlainResponseDto(true);
    }
}
