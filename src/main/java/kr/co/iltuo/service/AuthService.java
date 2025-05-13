package kr.co.iltuo.service;

import jakarta.servlet.http.*;
import kr.co.iltuo.dto.request.auth.*;
import kr.co.iltuo.dto.response.auth.*;

public interface AuthService {
    SignInResponseDto signInNative(HttpServletResponse response, NativeSignInRequestDto nativeSignInRequestDto);
    UserIdDuplicateCheckResponseDto idDuplicateCheck(UserIdDuplicateCheckRequestDto userIdDuplicateCheckRequestDto);
    SignInResponseDto signUpNative(HttpServletResponse response, NativeSignUpRequestDto nativeSignUpRequestDto);
    RefreshAccessTokenResponseDto refreshAccessToken(HttpServletRequest request, HttpServletResponse response);
    void logout(HttpServletRequest request, HttpServletResponse response);
}
