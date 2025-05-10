package ensharp.pinterest.domain.pin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "특정 Pin 수정 요청")
public class UpdatePinRequest {
    @Schema(description = "수정된 Pin 제목", example = "엔샵 뽀샵 사진")
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @Schema(description = "수정된 Pin 설명", example = "수욜날 찍은거 보정한거")
    private String description;
}
