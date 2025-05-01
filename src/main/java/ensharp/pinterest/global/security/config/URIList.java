package ensharp.pinterest.global.security.config;

public class URIList {
    public static final String[] WHITELIST = {
            "/auth/login", // 로그인
            "/auth/signup", // 회원가입
            "/auth/email-check", // 이메일 인증
            "/email-verification/*",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };
}
