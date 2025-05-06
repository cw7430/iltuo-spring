package kr.co.iltuo.controller;

import jakarta.validation.Valid;
import kr.co.iltuo.dto.request.auth.*;
import kr.co.iltuo.dto.response.auth.*;
import kr.co.iltuo.dto.response.ResponseDto;
import kr.co.iltuo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign_in_native")
    public ResponseDto<SignInResponseDto> signInNative(@Valid @RequestBody NativeSignInRequestDto nativeSignInRequestDto) {
        return ResponseDto.success(authService.signInNative(nativeSignInRequestDto));
    }

    @PostMapping("/check_id")
    public ResponseDto<UserIdDuplicateCheckResponseDto> idDuplicateCheck(@Valid @RequestBody UserIdDuplicateCheckRequestDto userIdDuplicateCheckRequestDto) {
        return ResponseDto.success(authService.idDuplicateCheck(userIdDuplicateCheckRequestDto));
    }

    @PostMapping("/sign_up_native")
    public ResponseDto<SignInResponseDto> signUpNative(@Valid @RequestBody NativeSignUpRequestDto nativeSignUpRequestDto) {
        return ResponseDto.success(authService.signUpNative(nativeSignUpRequestDto));
    }
}
