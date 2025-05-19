package kr.co.iltuo.service.auth;

import jakarta.servlet.http.*;
import kr.co.iltuo.dto.request.auth.*;
import kr.co.iltuo.dto.response.auth.*;
import kr.co.iltuo.entity.auth.NativeUserView;
import kr.co.iltuo.entity.auth.SocialUserView;

public interface AuthService {
    SignInResponseDto signInNative(HttpServletResponse response, NativeSignInRequestDto nativeSignInRequestDto);
    UserIdDuplicateCheckResponseDto idDuplicateCheck(UserIdDuplicateCheckRequestDto userIdDuplicateCheckRequestDto);
    SignInResponseDto signUpNative(HttpServletResponse response, NativeSignUpRequestDto nativeSignUpRequestDto);
    RefreshAccessTokenResponseDto refreshAccessToken(HttpServletRequest request, HttpServletResponse response);
    void logout(HttpServletRequest request, HttpServletResponse response);
    NativeUserView getNativeProfile(HttpServletRequest request);
    SocialUserView getSocialProfile(HttpServletRequest request);
}
