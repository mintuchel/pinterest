package ensharp.pinterest.domain.pin.service;

import ensharp.pinterest.domain.pin.dto.request.CreatePinRequest;
import ensharp.pinterest.domain.pin.dto.response.S3ObjectInfo;
import ensharp.pinterest.domain.pin.entity.Pin;
import ensharp.pinterest.domain.pin.repository.PinRepository;
import ensharp.pinterest.domain.user.entity.User;
import ensharp.pinterest.domain.user.repository.UserRepository;
import ensharp.pinterest.global.exception.errorcode.UserErrorCode;
import ensharp.pinterest.global.exception.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PinService {
    private final S3Service s3Service;
    private final PinRepository pinRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createPin(CreatePinRequest createPinRequest) {

        S3ObjectInfo s3ObjectInfo = s3Service.uploadImageToS3(createPinRequest.getImage());

        User user = userRepository.findByEmail(createPinRequest.getEmail())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Pin pin = Pin.builder()
                .title(createPinRequest.getTitle())
                .description(createPinRequest.getDescription())
                .user(user)
                .s3_key(s3ObjectInfo.s3Key())
                .s3_url(s3ObjectInfo.s3Url())
                .build();

        pinRepository.save(pin);
    }

    @Transactional
    public void deletePin(){

    }

    @Transactional
    public void updatePin(){

    }
}
