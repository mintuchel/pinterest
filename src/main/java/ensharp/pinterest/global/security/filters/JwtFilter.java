package ensharp.pinterest.global.security.filters;

import ensharp.pinterest.global.security.jwt.JwtUtil;
import ensharp.pinterest.global.security.model.JwtUserDetails;
import ensharp.pinterest.global.security.service.JwtUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("========== [ JwtFilter executed ] ==========");

        String accessToken = getAccessToken(request);

        if(accessToken == null) {
            System.out.println("token null");
            filterChain.doFilter(request, response);
            return;
        }else{
            System.out.println("token exists");
        }

        Claims claims;
        try{
            claims = jwtUtil.verifySignature(accessToken);
        }catch(JwtException e){
            System.out.println("invalid token!" + " ( " + e.getMessage() + " )");
            return;
        }

        System.out.println("existing token is valid! ( issued by our server )");

        if(jwtUtil.isExpired(claims)){
            System.out.println("token is expired!");
            filterChain.doFilter(request, response);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("valid but expired token!");
            return;
        }else{
            System.out.println("token is not expired!");
        }

        JwtUserDetails jwtUserDetails = new JwtUserDetails(jwtUtil.getEmail(claims), jwtUtil.getUsername(claims));

        Authentication authToken = new UsernamePasswordAuthenticationToken(jwtUserDetails, null, jwtUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private String getAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null) {
            System.out.println("request doesnt have 'Authorization' in header");
            return null;
        }

        if(!authorization.startsWith("Bearer ")) {
            System.out.println("request doesnt have bearer token");
            return null;
        }

        return authorization.substring(7);
    }
}
