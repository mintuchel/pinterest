package ensharp.pinterest.global.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import ensharp.pinterest.domain.user.dto.request.LoginRequest;
import ensharp.pinterest.global.security.jwt.JwtUtil;
import ensharp.pinterest.global.security.model.JwtUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("========== [ LoginFilter executed ] ==========");

        LoginRequest loginRequest;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            loginRequest = objectMapper.readValue(messageBody, LoginRequest.class);
        } catch(IOException e){
            throw new RuntimeException(e);
        }

        String email = loginRequest.email();
        String password = loginRequest.password();

        System.out.println("login success");
        System.out.println("email: " + email + ", password: " + password);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();

        String email = jwtUserDetails.getEmail();
        String username = jwtUserDetails.getUsername();

        String token = jwtUtil.createJwt(email, username, 5*60*1000L);

        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(401);
    }
}
