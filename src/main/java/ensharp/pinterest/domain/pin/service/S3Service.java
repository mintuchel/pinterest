package ensharp.pinterest.domain.pin.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import ensharp.pinterest.domain.pin.dto.response.S3ObjectInfo;
import ensharp.pinterest.global.config.S3Config;
import ensharp.pinterest.global.exception.errorcode.PinErrorCode;
import ensharp.pinterest.global.exception.exception.PinException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Config s3Config;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public S3ObjectInfo getImageInfoFromS3(String s3Key){
        boolean doesObjectExist = s3Config.amazonS3().doesObjectExist(bucket, s3Key);

        if(!doesObjectExist){
            throw new PinException(PinErrorCode.PIN_NOT_FOUND);
        }

        String publicUrl = getPublicUrl(s3Key);
        return new S3ObjectInfo(s3Key, publicUrl);
    }

    public S3ObjectInfo uploadImageToS3(MultipartFile file) {
        // 파일에 대한 S3 Key 값인 UUID 생성
        String originalFilename = file.getOriginalFilename(); // 김고은.jpeg
        String s3Key = UUID.randomUUID().toString() + "-" + originalFilename;

        // 파일 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        // S3에 파일 업로드 요청 생성
        // 이거 예외처리 나중에 커스텀 예외처리 해야함
        try {
            // S3에 저장할 객체 생성
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, s3Key, file.getInputStream(), metadata);
            // S3에 파일 업로드
            s3Config.amazonS3().putObject(putObjectRequest);
        }catch(IOException e) {

        }

        return new S3ObjectInfo(s3Key, getPublicUrl(s3Key));
    }

    public boolean deleteImageFromS3(String s3Key) {
        boolean doesObjectExist = s3Config.amazonS3().doesObjectExist(bucket, s3Key);
        if (doesObjectExist) {
            s3Config.amazonS3().deleteObject(bucket, s3Key);
        } else {
            throw new PinException(PinErrorCode.PIN_NOT_FOUND);
        }
        return true;
    }

    public boolean updateImageFromS3(String s3Key, MultipartFile file) {
        return true;
    }

    // S3에 저장 후 해당 URL 을 들어가면 이미지가 나옴
    // URL 을 데이터베이스에 저장하고 클라이언트에게 넘겨줄 생각이라 URL 형식으로 변환해주는 메서드를 추가적으로 작성
    // S3 객체의 URL 은 https://%s.s3.%s.amazonaws.com/%s과 같은 형식으로 저장됨
    // S3에 업로드된 파일을 인터넷에서 접근할 수 있는 주소(URL) = https://{버킷이름}.s3.{리전}.amazonaws.com/{S3 Key}
    private String getPublicUrl(String fileName){
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, s3Config.amazonS3().getRegionName(),fileName);
    }
}