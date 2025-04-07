package ensharp.pinterest.domain.pin.service;

import ensharp.pinterest.domain.pin.dto.request.CreatePinRequest;
import ensharp.pinterest.domain.pin.dto.request.DeletePinRequest;
import ensharp.pinterest.domain.pin.dto.response.PinInfoResponse;
import ensharp.pinterest.domain.pin.dto.response.PinThumbnailResponse;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class PinService {
    private final S3Service s3Service;
    private final PinRepository pinRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<PinThumbnailResponse> getPinThumbnails(String query){

        // 쿼리가 ""이면
        if(query.isBlank()){
            return pinRepository.findAll().stream()
                    .map(PinThumbnailResponse::from).toList();
        }

        // 아니면 해당 쿼리가 제목이나 설명에 포함된 Pin 들만 반환함
        return pinRepository.findAllByQuery(query).stream()
                .map(PinThumbnailResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public PinInfoResponse getPin(String pinId){

    }

    @Transactional
    public void createPin(CreatePinRequest createPinRequest) {

        S3ObjectInfo s3ObjectInfo = s3Service.uploadImageToS3(createPinRequest.getImage());

        User user = userRepository.findByEmail(createPinRequest.getEmail())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Pin pin = Pin.builder()
                .title(createPinRequest.getTitle())
                .description(createPinRequest.getDescription())
                .user(user)
                .authorName(user.getUsername())
                .s3Key(s3ObjectInfo.s3Key())
                .s3Url(s3ObjectInfo.s3Url())
                .build();

        pinRepository.save(pin);
    }

    @Transactional
    public void deletePin(DeletePinRequest deletePinRequest) {

        // pinId로 삭제할 Pin 객체 조회
        Pin targetPin = pinRepository.findById(deletePinRequest.pinId())
                .orElseThrow(()-> new PinException(PinErrorCode.PIN_NOT_FOUND));

        // s3Key로 S3에서 Pin 삭제
        boolean deleteSuccess = s3Service.deleteImageFromS3(targetPin.getS3Key());

        // Local DB에서도 삭제
        pinRepository.delete(targetPin);
    }

    @Transactional
    public void updatePin(){

    }
}
