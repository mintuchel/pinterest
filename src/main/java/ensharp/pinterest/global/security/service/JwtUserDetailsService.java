package ensharp.pinterest.global.security.service;

import ensharp.pinterest.domain.user.entity.User;
import ensharp.pinterest.domain.user.repository.UserRepository;
import ensharp.pinterest.global.exception.errorcode.UserErrorCode;
import ensharp.pinterest.global.exception.exception.UserException;
import ensharp.pinterest.global.security.model.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        if(user!=null){
            return new JwtUserDetails(user);
        }

        return null;
    }
}
