package kr.co.iltuo.contants.auth;

import kr.co.iltuo.contants.EndPoint;
import lombok.*;

@Getter
@AllArgsConstructor
public enum AuthEndpoint {
    SIGN_IN(EndPoint.BASE + "/auth/sign_in", "로그인", "POST"),
    SIGN_UP(EndPoint.BASE + "/auth/sign_up", "회원가입", "POST"),
    CHECK_ID(EndPoint.BASE  + "/auth/check_id", "아이디 중복체크", "POST");

    private final String path;
    private final String description;
    private final String httpMethod;
}