package kr.co.iltuo.service.order;

import jakarta.servlet.http.*;
import kr.co.iltuo.common.code.ResponseCode;
import kr.co.iltuo.common.exception.CustomException;
import kr.co.iltuo.dto.request.IdxRequestDto;
import kr.co.iltuo.dto.request.order.*;
import kr.co.iltuo.dto.response.PlainResponseDto;
import kr.co.iltuo.entity.auth.*;
import kr.co.iltuo.entity.order.*;
import kr.co.iltuo.repository.auth.*;
import kr.co.iltuo.repository.order.*;
import kr.co.iltuo.security.jwt.JwtProvider;
import kr.co.iltuo.service.order.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImplement implements OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartOptionRepository cartOptionRepository;
    private final CartViewRepository cartViewRepository;
    private final CartOptionViewRepository cartOptionViewRepository;
    private final JwtProvider jwtProvider;

    private User getUserByToken(HttpServletRequest request) {
        String userId = jwtProvider.extractUserIdFromRequest(request, "ACCESS");
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ResponseCode.RESOURCE_NOT_FOUND));
    }

    @Override
    @Transactional
    public PlainResponseDto addCart(HttpServletRequest request, AddCartRequestDto addCartRequestDto) {
        User user = getUserByToken(request);
        Cart cart = OrderEntityUtil.insertCart(addCartRequestDto, user);
        cartRepository.save(cart);

        List<CartOption> cartOptions = OrderEntityUtil.insertCartOptions(addCartRequestDto, cart);
        cartOptionRepository.saveAll(cartOptions);
        return new PlainResponseDto(true);
    }

    @Override
    public List<CartView> cartList(HttpServletRequest request) {
        User user = getUserByToken(request);
        return cartViewRepository.findByUserIdx(user.getUserIdx());
    }

    @Override
    public List<CartOptionView> cartOptionList(HttpServletRequest request) {
        User user = getUserByToken(request);
        return cartOptionViewRepository.findByUserIdx(user.getUserIdx());
    }

    @Override
    @Transactional
    public PlainResponseDto deleteCartSingle(HttpServletRequest request, IdxRequestDto idxRequestDto) {
        String token = jwtProvider.extractAccessTokenFromCookie(request);
        if (!StringUtils.hasText(token) || !jwtProvider.validateAccessToken(token)) {
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }

        long optionDeleteCount = cartOptionRepository.deleteByCartId(idxRequestDto.getIdx());
        log.info("Deleted {} cartOption(s) for cartId: {}", optionDeleteCount, idxRequestDto.getIdx());

        cartRepository.deleteById(idxRequestDto.getIdx());
        
        return new PlainResponseDto(true);
    }

}
