package kr.co.iltuo.service;

import kr.co.iltuo.dto.request.auth.*;
import kr.co.iltuo.dto.response.auth.*;

public interface AuthService {
    SignInResponseDto signInNative(NativeSignInRequestDto nativeSignInRequestDto);
    UserIdDuplicateCheckResponseDto idDuplicateCheck(UserIdDuplicateCheckRequestDto userIdDuplicateCheckRequestDto);
    SignInResponseDto signUpNative(NativeSignUpRequestDto nativeSignUpRequestDto);
}
