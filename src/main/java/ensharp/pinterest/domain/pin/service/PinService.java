package ensharp.pinterest.domain.pin.service;

import ensharp.pinterest.domain.pin.dto.request.CreatePinRequest;
import ensharp.pinterest.domain.pin.dto.request.DeletePinRequest;
import ensharp.pinterest.domain.pin.dto.response.S3ObjectInfo;
import ensharp.pinterest.domain.pin.entity.Pin;
import ensharp.pinterest.domain.pin.repository.PinRepository;
import ensharp.pinterest.domain.user.entity.User;
import ensharp.pinterest.domain.user.repository.UserRepository;
import ensharp.pinterest.global.exception.errorcode.PinErrorCode;
import ensharp.pinterest.global.exception.errorcode.UserErrorCode;
import ensharp.pinterest.global.exception.exception.PinException;
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
                .s3Key(s3ObjectInfo.s3Key())
                .s3Url(s3ObjectInfo.s3Url())
                .build();

        pinRepository.save(pin);
    }

    @Transactional
    public void deletePin(DeletePinRequest deletePinRequest) {

        // 삭제할 핀 조회
        Pin targetPin = pinRepository.findByS3Key(deletePinRequest.s3key())
                .orElseThrow(()-> new PinException(PinErrorCode.PIN_NOT_FOUND));

        boolean deleteSuccess = s3Service.deleteImageFromS3(deletePinRequest.s3key());

        // Local DB에서도 삭제
        pinRepository.delete(targetPin);
    }

    @Transactional
    public void updatePin(){

    }
}
