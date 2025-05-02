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

    @PostMapping("/sign_in")
    public ResponseDto<SignInResponseDto> signIn(@Valid @RequestBody SignInRequestDto signInRequestDto) {
        return ResponseDto.success(authService.signIn(signInRequestDto));
    }

    @PostMapping("/check_id")
    public ResponseDto<UserIdDuplicateCheckResponseDto> idDuplicateCheck(@Valid @RequestBody UserIdDuplicateCheckRequestDto userIdDuplicateCheckRequestDto) {
        return ResponseDto.success(authService.idDuplicateCheck(userIdDuplicateCheckRequestDto));
    }

    @PostMapping("/sign_up")
    public ResponseDto<SignInResponseDto> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        return ResponseDto.success(authService.signUp(signUpRequestDto));
    }
}
