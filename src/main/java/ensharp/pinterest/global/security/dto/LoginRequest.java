package ensharp.pinterest.global.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "로그인 요청")
public record LoginRequest(
        @Email(message = "유효한 이메일 형식을 입력하세요.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Schema(description = "이메일 주소", example = "ensharp25@naver.com")
        String email,
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Schema(description = "비밀번호", example = "ensharp25화이팅")
        String password
) { }
