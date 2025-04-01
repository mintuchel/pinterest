package ensharp.pinterest.domain.pin.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

// form-data 를 @RequestPart 로 각각 값을 받지 않고 한번에 받으려면 @ModelAttribute 를 사용하여 클래스에 매핑가능
// 매핑할때 @Setter 가 존재해야 Spring 이 필드값을 직접 설정할 수 있음
@Setter
@Getter
public class CreatePinRequest {
    private String email;
    private String title;
    private String description;

    private MultipartFile image;
}
