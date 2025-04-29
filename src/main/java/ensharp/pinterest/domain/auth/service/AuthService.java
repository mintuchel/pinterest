package ensharp.pinterest.domain.auth.service;

import ensharp.pinterest.domain.auth.dto.ChangePasswordRequest;
import ensharp.pinterest.domain.auth.dto.CheckEmailRequest;
import ensharp.pinterest.domain.auth.dto.SignUpRequest;
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
public class AuthService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public String checkIfEmailAvailable(CheckEmailRequest checkEmailRequest){
        // 만약 이메일이 중복되었으면 예외 던지기
        if(userRepository.existsByEmail(checkEmailRequest.email())) {
            throw new UserException(UserErrorCode.DUPLICATE_EMAIL);
        }

        return "사용 가능한 이메일입니다";
    }

    @Transactional
    public String signUp(SignUpRequest signUpRequest){

        // 이미 해당 이메일 유저가 존재한다면 예외 던지기
        if(userRepository.existsByEmail(signUpRequest.email())){
            throw new UserException(UserErrorCode.DUPLICATE_EMAIL);
        }

        User user = User.builder()
                .email(signUpRequest.email())
                .username(signUpRequest.username())
                // 비밀번호는 인코딩하여 저장
                .password(passwordEncoder.encode(signUpRequest.password()))
                .streetAddress(signUpRequest.streetAddress())
                .detailAddress(signUpRequest.detailAddress())
                .build();

        userRepository.save(user);

        return user.getId();
    }

    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        // 새로운 password 인코딩해주기
        String newPassword = passwordEncoder.encode(changePasswordRequest.password());

        int rows = userRepository.updatePasswordByEmail(changePasswordRequest.email(), newPassword);

        // 업데이트 된 행이 없다면
        if(rows == 0)
            throw new UserException(UserErrorCode.PASSWORD_UPDATE_FAILURE);
    }
}
