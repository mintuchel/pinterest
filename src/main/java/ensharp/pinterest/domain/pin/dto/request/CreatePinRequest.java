package ensharp.pinterest.domain.pin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

// form-data 를 @RequestPart 로 각각 값을 받지 않고 한번에 받으려면 @ModelAttribute 를 사용하여 클래스에 매핑가능
// 매핑할때 @Setter 가 존재해야 Spring 이 필드값을 직접 설정할 수 있음
@Setter
@Getter
public class CreatePinRequest {
    @Schema(description = "핀 제목", example = "엔샵사진")
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @Schema(description = "핀 설명", example = "저번주 수요일날 찍은거")
    private String description;

    private MultipartFile image;
}
