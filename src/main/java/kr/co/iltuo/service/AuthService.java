package kr.co.iltuo.service;

import kr.co.iltuo.dto.request.auth.*;
import kr.co.iltuo.dto.response.auth.*;

public interface AuthService {
    public SignInResponseDto signInNative(NativeSignInRequestDto nativeSignInRequestDto);
    public UserIdDuplicateCheckResponseDto idDuplicateCheck(UserIdDuplicateCheckRequestDto userIdDuplicateCheckRequestDto);
    public SignInResponseDto signUpNative(NativeSignUpRequestDto nativeSignUpRequestDto);
}
