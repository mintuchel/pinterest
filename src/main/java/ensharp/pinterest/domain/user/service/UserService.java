package ensharp.pinterest.domain.user.service;

import ensharp.pinterest.domain.user.dto.response.UserInfoResponse;
import ensharp.pinterest.domain.auth.dto.ChangePasswordRequest;
import ensharp.pinterest.domain.user.entity.User;
import ensharp.pinterest.domain.user.repository.UserRepository;
import ensharp.pinterest.global.exception.errorcode.UserErrorCode;
import ensharp.pinterest.global.exception.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUserById(String userId){
        return userRepository.findById(userId)
                .orElseThrow(()-> new UserException(UserErrorCode.USER_NOT_FOUND));
    }
}