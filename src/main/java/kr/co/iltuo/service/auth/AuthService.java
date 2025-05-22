package kr.co.iltuo.service.auth;

import jakarta.servlet.http.*;
import kr.co.iltuo.dto.request.*;
import kr.co.iltuo.dto.request.auth.*;
import kr.co.iltuo.dto.response.PlainResponseDto;
import kr.co.iltuo.dto.response.auth.*;
import kr.co.iltuo.entity.auth.*;

import java.util.*;

public interface AuthService {
    SignInResponseDto signInNative(HttpServletResponse response, NativeSignInRequestDto nativeSignInRequestDto);
    PlainResponseDto idDuplicateCheck(UserIdDuplicateCheckRequestDto userIdDuplicateCheckRequestDto);
    SignInResponseDto signUpNative(HttpServletResponse response, NativeSignUpRequestDto nativeSignUpRequestDto);
    RefreshAccessTokenResponseDto refreshAccessToken(HttpServletRequest request, HttpServletResponse response);
    void logout(HttpServletRequest request, HttpServletResponse response);
    NativeUserView getNativeProfile(HttpServletRequest request);
    SocialUserView getSocialProfile(HttpServletRequest request);
    List<Address> getUserAddressList(HttpServletRequest request);
    PlainResponseDto changePassword(HttpServletRequest request, PasswordRequestDto passwordRequestDto);
    PlainResponseDto changeProfile(HttpServletRequest request, ProfileRequestDto profileRequestDto);
    PlainResponseDto addAddress(HttpServletRequest request, AddressRequestDto addressRequestDto);
    PlainResponseDto changeMainAddress(HttpServletRequest request, IdxRequestDto idxRequestDto);
    PlainResponseDto invalidateAddresses(HttpServletRequest request, List<IdxRequestDto> idxRequests);
}
