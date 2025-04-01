package ensharp.pinterest.domain.pin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "핀 삭제 요청")
public record DeletePinRequest(
        @Email(message = "유효한 이메일 형식을 입력하세요.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Schema(description = "이메일 주소", example = "ensharp25@naver.com")
        String email,
        @NotBlank(message = "s3 키값은 필수 입력 값입니다.")
        @Schema(description = "s3key", example = "1a2b3c4d5e6f7g8h")
        String s3key
) { }
