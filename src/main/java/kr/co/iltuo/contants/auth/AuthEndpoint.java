package kr.co.iltuo.contants.auth;

import kr.co.iltuo.contants.EndPoint;
import lombok.*;

@Getter
@AllArgsConstructor
public enum AuthEndpoint {
    SIGN_IN_NATIVE(EndPoint.BASE + "/auth/sign_in_native", "로그인", "POST"),
    SIGN_UP_NATIVE(EndPoint.BASE + "/auth/sign_up_native", "회원가입", "POST"),
    CHECK_ID(EndPoint.BASE  + "/auth/check_id", "아이디 중복체크", "POST"),
    REFRESH_TOKEN(EndPoint.BASE  + "/auth/refresh_Token", "토큰 재발급", "POST"),
    LOG_OUT(EndPoint.BASE + "/auth/logout", "로그아웃", "POST"),
    Oauth2(EndPoint.BASE + "/auth/social/**", "소셜로그인 콜백", "POST");

    private final String path;
    private final String description;
    private final String httpMethod;
}