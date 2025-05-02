package kr.co.iltuo.service;

import kr.co.iltuo.dto.request.auth.*;
import kr.co.iltuo.dto.response.auth.*;

public interface AuthService {
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto);
    public UserIdDuplicateCheckResponseDto idDuplicateCheck(UserIdDuplicateCheckRequestDto userIdDuplicateCheckRequestDto);
    public SignInResponseDto signUp(SignUpRequestDto signUpRequestDto);
}
