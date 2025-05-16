package kr.co.iltuo.controller.auth;

import jakarta.servlet.http.*;
import jakarta.validation.Valid;
import kr.co.iltuo.dto.request.auth.*;
import kr.co.iltuo.dto.response.auth.*;
import kr.co.iltuo.dto.response.ResponseDto;
import kr.co.iltuo.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public ResponseDto<UserIdDuplicateCheckResponseDto> idDuplicateCheck(@Valid @RequestBody UserIdDuplicateCheckRequestDto userIdDuplicateCheckRequestDto) {
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
}
