package ensharp.pinterest.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// CSRF (Cross Site Request Forgery)
// 사이트 간 요청 위조 공격을 방지하기 위해 Spring Security가 기본적으로 제공하는 보안 기능
// 왜 비활성화 하는지는 그리고 정확히 뭔지는 추후에 공부

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/user/**").permitAll() // 모든 컨트롤러 공개
                        .requestMatchers("/api/v1/email-verification/**").permitAll()
                        .requestMatchers("/api/v1/admin/**").permitAll()
                        .requestMatchers("/api/v1/pin/**").permitAll()
                        .requestMatchers("/api/v1/favorite/**").permitAll()
                        .requestMatchers("/api/v1/comment/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // swagger 외부 공개
                        .anyRequest().authenticated() // 나머지는 인증 필요
                )
                .csrf(csrf -> csrf.disable()); // CSRF 비활성화 -> 이거 왜 함??
        return http.build();
    }
}
