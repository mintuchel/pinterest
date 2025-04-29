package ensharp.pinterest.global.security.model;


import ensharp.pinterest.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * UserDetails Interface 를 구현
 *
 * Spring Security 에서 사용자 정보를 담는 DTO 형식인 UserDetails Interface 를 구현
 * 실제 유저들을 저장하는 User 엔티티를 감싸고 있음
 */

public class JwtUserDetails implements UserDetails {

    private final User user;

    public JwtUserDetails(User user) {
        this.user = user;
    }

    public JwtUserDetails(UUID id, String email, String username){
        this.user = User.builder()
                .id(id)
                .email(email)
                .password("1234")
                .username(username)
                .build();
    }

    public UUID getId() {
        return user.getId();
    }

    public String getEmail(){
        return user.getEmail();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){ return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 권한 없음
    }
}
