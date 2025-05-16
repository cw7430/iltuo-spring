package kr.co.iltuo.service.global.util;

import jakarta.servlet.http.*;

public class CookieUtil {
    public static void addHttpOnlyCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);
    }

    public static void removeCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 즉시 삭제
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);
    }

    // cookie.setMaxAge((int)((accessTokenResponseDto.accessTokenExpiresAt() - System.currentTimeMillis()) / 1000));
}
