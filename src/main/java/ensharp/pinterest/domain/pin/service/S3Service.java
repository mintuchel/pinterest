package ensharp.pinterest.domain.pin.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import ensharp.pinterest.global.config.S3Config;
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

    public String createPin(MultipartFile image) throws IOException {
        // 고유한 파일 이름 생성
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

        // 파일 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());

        // S3에 파일 업로드 요청 생성
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, image.getInputStream() , metadata);

        // S3에 파일 업로드
        s3Config.amazonS3().putObject(putObjectRequest);

        return getPublicUrl(fileName);
    }

    // S3에 저장 후 해당 URL 을 들어가면 이미지가 나옴
    // URL 을 데이터베이스에 저장하고 클라이언트에게 넘겨줄 생각이라 URL 형식으로 변환해주는 메서드를 추가적으로 작성
    // S3 객체의 URL 은 https://%s.s3.%s.amazonaws.com/%s과 같은 형식으로 저장됨
    private String getPublicUrl(String fileName){
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, s3Config.amazonS3().getRegionName(),fileName);
    }
}
