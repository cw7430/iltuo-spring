package kr.co.iltuo.config;

import kr.co.iltuo.contants.auth.AuthEndpoint;
import kr.co.iltuo.contants.product.ProductEndpoint;
import kr.co.iltuo.security.jwt.JwtAuthenticationFilter;
import kr.co.iltuo.security.jwt.JwtProvider;
import kr.co.iltuo.security.oauth.OAuth2SuccessHandler;
import kr.co.iltuo.service.auth.Oauth2UserServiceImplement;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtProvider jwtProvider;
    private final Oauth2UserServiceImplement oauth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                AuthEndpoint.SIGN_IN_NATIVE.getPath(), AuthEndpoint.SIGN_UP_NATIVE.getPath(), AuthEndpoint.CHECK_ID.getPath(),
                                AuthEndpoint.REFRESH_TOKEN.getPath(), AuthEndpoint.LOG_OUT.getPath(), AuthEndpoint.Oauth2.getPath() + "/**",
                                ProductEndpoint.MAJOR_CATEGORY_LIST.getPath(), ProductEndpoint.RECOMMENDED_PRODUCT_LIST.getPath(),
                                ProductEndpoint.MAJOR_CATEGORY.getPath(), ProductEndpoint.MINER_CATEGORY_LIST.getPath(),
                                ProductEndpoint.PRODUCT_LIST.getPath(), ProductEndpoint.PRODUCT_DETAIL.getPath(),
                                ProductEndpoint.OPTION_LIST.getPath(), ProductEndpoint.OPTION_DETAIL_LIST.getPath()
                        ).permitAll()
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endPoint -> endPoint.baseUri(AuthEndpoint.Oauth2.getPath()))
                        .redirectionEndpoint(endPoint -> endPoint.baseUri(AuthEndpoint.Oauth2.getPath() + "/callback/*"))
                        .userInfoEndpoint(endPoint -> endPoint.userService(oauth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
