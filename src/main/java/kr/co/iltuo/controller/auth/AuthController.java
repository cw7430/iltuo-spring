package kr.co.iltuo.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.co.iltuo.dto.request.IdxRequestDto;
import kr.co.iltuo.dto.request.auth.*;
import kr.co.iltuo.dto.response.PlainResponseDto;
import kr.co.iltuo.dto.response.ResponseDto;
import kr.co.iltuo.dto.response.auth.RefreshAccessTokenResponseDto;
import kr.co.iltuo.dto.response.auth.SignInResponseDto;
import kr.co.iltuo.entity.auth.Address;
import kr.co.iltuo.entity.auth.NativeUserView;
import kr.co.iltuo.entity.auth.SocialUserView;
import kr.co.iltuo.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/change_password")
    public ResponseDto<PlainResponseDto> changePassword(HttpServletRequest request, @Valid @RequestBody PasswordRequestDto passwordRequestDto) {
        return ResponseDto.success(authService.changePassword(request, passwordRequestDto));
    }

    @PostMapping("/change_profile")
    public ResponseDto<PlainResponseDto> changeProfile(HttpServletRequest request, @Valid @RequestBody ProfileRequestDto profileRequestDto) {
        return ResponseDto.success(authService.changeProfile(request, profileRequestDto));
    }

    @PostMapping("/add_address")
    public ResponseDto<PlainResponseDto> addAddress(HttpServletRequest request, @Valid @RequestBody AddressRequestDto addressRequestDto) {
        return ResponseDto.success(authService.addAddress(request, addressRequestDto));
    }

    @PostMapping("/change_main_address")
    public ResponseDto<PlainResponseDto> changeMainAddress(HttpServletRequest request, @RequestBody IdxRequestDto idxRequestDto) {
        return ResponseDto.success(authService.changeMainAddress(request, idxRequestDto));
    }

    @PostMapping("/invalidate_addresses")
    public ResponseDto<PlainResponseDto> invalidateAddresses(HttpServletRequest request, @RequestBody List<IdxRequestDto> idxRequests) {
        return ResponseDto.success(authService.invalidateAddresses(request, idxRequests));
    }
}
