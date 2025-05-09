package ensharp.pinterest.domain.user.service;

import ensharp.pinterest.domain.pin.dto.response.PinThumbnailResponse;
import ensharp.pinterest.domain.pin.service.PinService;
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

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PinService pinService;

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUserById(String userId){
        return userRepository.findById(userId)
                .orElseThrow(()-> new UserException(UserErrorCode.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public UserInfoResponse getUserByUsername(String username){
        User user =  userRepository.findByUsername(username)
                .orElseThrow(()-> new UserException(UserErrorCode.USER_NOT_FOUND));

        return UserInfoResponse.from(user);
    }

    @Transactional(readOnly = true)
    public List<PinThumbnailResponse> getPinsByUsername(String username){
        if(!userRepository.existsByUsername(username)){
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }

        return pinService.getPinsByUsername(username);
    }
}