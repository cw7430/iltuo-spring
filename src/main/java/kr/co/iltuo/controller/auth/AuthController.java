package kr.co.iltuo.controller.auth;

import jakarta.servlet.http.*;
import jakarta.validation.Valid;
import kr.co.iltuo.dto.request.IdxSingleRequestDto;
import kr.co.iltuo.dto.request.auth.*;
import kr.co.iltuo.dto.response.auth.*;
import kr.co.iltuo.dto.response.*;
import kr.co.iltuo.entity.auth.*;
import kr.co.iltuo.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign_in_native")
    public ResponseDto<SignInResponseDto> signInNative(HttpServletResponse response, @Valid @RequestBody NativeSignInRequestDto nativeSignInRequestDto) {
        return ResponseDto.success(authService.signInNative(response, nativeSignInRequestDto));
    }

    @PostMapping("/check_id")
    public ResponseDto<PlainResponseDto> idDuplicateCheck(@Valid @RequestBody UserIdDuplicateCheckRequestDto userIdDuplicateCheckRequestDto) {
        return ResponseDto.success(authService.idDuplicateCheck(userIdDuplicateCheckRequestDto));
    }

    @PostMapping("/sign_up_native")
    public ResponseDto<SignInResponseDto> signUpNative(HttpServletResponse response, @Valid @RequestBody NativeSignUpRequestDto nativeSignUpRequestDto) {
        return ResponseDto.success(authService.signUpNative(response, nativeSignUpRequestDto));
    }

    @GetMapping("/refresh_Token")
    public ResponseDto<RefreshAccessTokenResponseDto> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseDto.success(authService.refreshAccessToken(request, response));
    }

    @GetMapping("/logout")
    public ResponseDto<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseDto.success(null);
    }

    @GetMapping("/native_profile")
    public ResponseDto<NativeUserView> getNativeProfile(HttpServletRequest request) {
        return ResponseDto.success(authService.getNativeProfile(request));
    }

    @GetMapping("/social_profile")
    public ResponseDto<SocialUserView> getSocialProfile(HttpServletRequest request) {
        return ResponseDto.success(authService.getSocialProfile(request));
    }

    @GetMapping("/address_list")
    public ResponseDto<List<Address>> getUserAddressList(HttpServletRequest request) {
        return ResponseDto.success(authService.getUserAddressList(request));
    }

    @PostMapping("/add_address")
    public ResponseDto<PlainResponseDto> addAddress(HttpServletRequest request, @Valid @RequestBody AddressRequestDto addressRequestDto) {
        return ResponseDto.success(authService.addAddress(request, addressRequestDto));
    }

    @PostMapping("/change_main_address")
    public ResponseDto<PlainResponseDto> changeMainAddress(HttpServletRequest request, IdxSingleRequestDto idxSingleRequestDto) {
        return ResponseDto.success(authService.changeMainAddress(request, idxSingleRequestDto));
    }
}
