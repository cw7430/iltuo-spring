package kr.co.iltuo.security.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.iltuo.dto.response.auth.AccessTokenResponseDto;
import kr.co.iltuo.dto.response.auth.RefreshTokenResponseDto;
import kr.co.iltuo.entity.auth.RefreshToken;
import kr.co.iltuo.entity.auth.User;
import kr.co.iltuo.repository.auth.RefreshTokenRepository;
import kr.co.iltuo.security.jwt.JwtProvider;
import kr.co.iltuo.service.auth.util.AuthConvertUtil;
import kr.co.iltuo.service.auth.util.AuthEntityUtil;
import kr.co.iltuo.service.auth.util.AuthTokenUtil;
import kr.co.iltuo.service.global.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    private void upsertRefreshToken(User user, RefreshTokenResponseDto refreshTokenResponseDto) {
        RefreshToken refreshToken = refreshTokenRepository.findById(user.getUserIdx())
                .map(existingToken -> {
                    AuthEntityUtil.updateRefreshToken(refreshTokenResponseDto, existingToken);
                    return existingToken;
                })
                .orElseGet(() -> AuthEntityUtil.insertRefreshToken(refreshTokenResponseDto, user));
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        User user = oAuth2User.getUser();

        String userPermission = AuthConvertUtil.convertPermission(user.getUserPermissionsCode());
        String authMethod = AuthConvertUtil.convertAuthMethodToString(user.getAuthMethodCode());

        AccessTokenResponseDto accessTokenResponseDto = AuthTokenUtil.createAccessToken(jwtProvider, user);
        CookieUtil.addHttpOnlyCookie(response, "accessToken", accessTokenResponseDto.accessToken(), -1);
        RefreshTokenResponseDto refreshTokenResponseDto = AuthTokenUtil.createRefreshToken(jwtProvider, user);
        CookieUtil.addHttpOnlyCookie(response, "refreshToken", refreshTokenResponseDto.refreshToken(), -1);
        upsertRefreshToken(user, refreshTokenResponseDto);

        response.sendRedirect("http://localhost:3000/oauth2/success?accessTokenExpiresAt="
                + accessTokenResponseDto.accessTokenExpiresAt() +
                "&refreshTokenExpiresAt=" + refreshTokenResponseDto.refreshTokenExpiresAt() +
                "&userPermission=" + userPermission +
                "&authMethod=" + authMethod);
    }
}
