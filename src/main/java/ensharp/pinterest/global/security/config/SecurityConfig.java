package ensharp.pinterest.global.security.config;

import ensharp.pinterest.global.security.filters.JwtFilter;
import ensharp.pinterest.global.security.filters.LoginFilter;
import ensharp.pinterest.global.security.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // AuthenticationManager 에 필요한 AuthenticationConfiguration 정의
    private final AuthenticationConfiguration authenticationConfiguration;

    // JwtFilter 와 LoginFilter 에 필요한 JwtUtil 정의
    private final JwtUtil jwtUtil;

    // 기본 생성자
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }

    // LoginFilter 에서 사용할 AuthenticationManager 를 @Bean 으로 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // AuthenticationManager 에 필요한 bCryptPasswordEncoder 를 @Bean 으로 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                // 정의한 CorsConfig 를 SecurityFilterChain 에 껴넣어주기
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf((auth) -> auth.disable())
                .formLogin((auth) -> auth.disable())
                .httpBasic((auth) -> auth.disable())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/auth/login", "/auth/signup", "/auth/email-check", "/email-verification/*").permitAll() // 로그인 회원가입 쪽 접근 허용
                        .requestMatchers("/users/**").permitAll() // 유저 정보 쪽 접근 다 뚫어주기
                        .requestMatchers("/admin/**").permitAll() // admin 쪽 접근 허용
                        .requestMatchers(HttpMethod.GET,"/pins/**").permitAll() // Pin 에 대한 댓글 접근이랑 Pin 조회 허용
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // swagger 접근 허용
                        .anyRequest().authenticated() // 나머지는 인증 필요
                )

                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class) // Jwt 필터는 로그인필터 전에 붙여야함
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}