package ensharp.pinterest.domain.pin.service;

import ensharp.pinterest.domain.pin.dto.request.CreatePinRequest;
import ensharp.pinterest.domain.pin.dto.request.UpdatePinRequest;
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

    // 원래 UserService 로 했는데 순환참조 문제때문에 일단 임시방편으로 repository 참조하게 만듬
    private final UserRepository userRepository;
    private final PinRepository pinRepository;

    /**
     * 특정 Pin 조회
     */
    @Transactional(readOnly = true)
    public Pin getPinById(String pinId){
        return pinRepository.findById(pinId)
                .orElseThrow(() -> new PinException(PinErrorCode.PIN_NOT_FOUND));
    }

    /**
     * 특정 유저가 올린 Pin 조회
     */
    @Transactional(readOnly = true)
    public List<PinThumbnailResponse> getPinsByUsername(String username){
        return pinRepository.findAllByUserName(username).stream()
                .map(PinThumbnailResponse::from).toList();
    }

    /**
     * query에 따른 List<PinThumbnail> 조회
     */
    @Transactional(readOnly = true)
    public List<PinThumbnailResponse> getPinsByQuery(String query){

        // 쿼리가 ""이면
        if(query.isBlank()){
            return pinRepository.findAll().stream()
                    .map(PinThumbnailResponse::from).toList();
        }

        // 아니면 해당 쿼리가 제목이나 설명에 포함된 Pin 들만 반환함
        return pinRepository.findAllByQuery(query).stream()
                .map(PinThumbnailResponse::from).toList();
    }

    @Transactional
    public String createPin(String userId, CreatePinRequest createPinRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        S3ObjectInfo s3ObjectInfo = s3Service.uploadImageToS3(createPinRequest.getImage());

        Pin pin = Pin.builder()
                .title(createPinRequest.getTitle())
                .description(createPinRequest.getDescription())
                .user(user)
                .authorName(user.getUsername())
                .s3Key(s3ObjectInfo.s3Key())
                .s3Url(s3ObjectInfo.s3Url())
                .build();

        pinRepository.save(pin);

        // 클라이언트에서 바로 리다이렉션 가능하도록 s3Url 반환
        return pin.getS3Url();
    }

    @Transactional
    public void deletePin(String userId, String pinId) {

        // pinId로 삭제할 Pin 객체 조회
        Pin targetPin = pinRepository.findById(pinId)
                .orElseThrow(() -> new PinException(PinErrorCode.PIN_NOT_FOUND));

        // 해당 Pin 을 올린 유저가 아니라면
        if(!targetPin.getUser().getId().equals(userId)){
            throw new PinException(PinErrorCode.PIN_ACCESS_DENIED);
        }

        // s3Key 로 S3에서 Pin 삭제
        boolean deleteSuccess = s3Service.deleteImageFromS3(targetPin.getS3Key());

        // S3 삭제 성공 시 DB 에서도 삭제
        pinRepository.delete(targetPin);
    }

    @Transactional
    public void updatePin(String userId, String pinId, UpdatePinRequest updatePinRequest) {
        // pinId로 업데이트할 Pin 객체 조회
        Pin targetPin = pinRepository.findById(pinId)
                .orElseThrow(()-> new PinException(PinErrorCode.PIN_NOT_FOUND));

        // 해당 Pin 을 올린 유저가 아니라면
        if(!targetPin.getUser().getId().equals(userId)){
            throw new PinException(PinErrorCode.PIN_ACCESS_DENIED);
        }

        String newTitle = updatePinRequest.getTitle().isBlank() ? targetPin.getTitle() : updatePinRequest.getTitle();
        String newDescription = updatePinRequest.getDescription().isBlank() ? targetPin.getDescription() : updatePinRequest.getDescription();

        pinRepository.updatePin(pinId, newTitle, newDescription);
    }
}