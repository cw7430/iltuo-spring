package kr.co.iltuo.contants.auth;

import kr.co.iltuo.contants.EndPoint;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthEndpoint {
    SIGN_IN_NATIVE(EndPoint.BASE + "/auth/sign_in_native", "로그인", "POST"),
    SIGN_UP_NATIVE(EndPoint.BASE + "/auth/sign_up_native", "회원가입", "POST"),
    CHECK_ID(EndPoint.BASE  + "/auth/check_id", "아이디 중복체크", "POST"),
    REFRESH_TOKEN(EndPoint.BASE  + "/auth/refresh_Token", "토큰 재발급", "GET"),
    LOG_OUT(EndPoint.BASE + "/auth/logout", "로그아웃", "GET"),
    Oauth2(EndPoint.BASE + "/auth/social", "소셜로그인", "POST"),
    NATIVE_PROFILE(EndPoint.BASE + "/auth/native_profile", "내부 회원", "GET"),
    SOCIAL_PROFILE(EndPoint.BASE + "/auth/social_profile", "소셜 회원", "GET"),
    ADDRESS_LIST(EndPoint.BASE + "/auth/address_list", "주소목록", "GET"),
    CHANGE_PASSWORD(EndPoint.BASE + "/auth/change_password", "비밀번호 변경", "POST"),
    CHANGE_PROFILE(EndPoint.BASE + "/auth/change_profile", "비밀번호 변경", "POST"),
    ADD_ADDRESS(EndPoint.BASE + "/auth/add_address", "주소등록", "POST"),
    CHANGE_MAIN_ADDRESS(EndPoint.BASE + "/auth/change_main_address", "메인 주소 수정", "POST"),
    INVALIDATE_ADDRESSES(EndPoint.BASE + "/auth/invalidate_addresses", "주소 삭제", "POST");

    private final String path;
    private final String description;
    private final String httpMethod;
}