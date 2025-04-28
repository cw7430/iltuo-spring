package kr.co.iltuo.config;

import org.springframework.context.annotation.*;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.*;
import java.util.*;

@Configuration
public class CustomCorsFilter {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // React 개발 서버 주소를 명시적으로 지정
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));

        // 허용할 HTTP 메서드
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 허용할 HTTP 헤더
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));

        // 자격 증명(Credentials)을 허용
        corsConfiguration.setAllowCredentials(true);

        // URL 기반으로 CORS 설정 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }
}