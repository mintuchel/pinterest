package ensharp.pinterest.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

/**
 * singletonList 는 불변리스트이고, 크기가 1일때 사용. List.of() 보다 성능상 약간 가볍다
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // https 도 추가할까??
        // localhost 라는 도메인의 모든 포트에서 request 를 쏠 수 있도록 설정
        configuration.setAllowedOriginPatterns(Collections.singletonList("http://localhost:*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        // Authorization Content-Type Origin 등을 명시하는 것
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}