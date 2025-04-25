package ensharp.pinterest.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "회원가입 요청")
public record SignUpRequest(
        // 내부적인 정규식으로 통해 email 형식인지 판단
        @Email(message = "유효한 이메일 형식을 입력하세요.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Size(message = "이메일은 30자를 넘을 수 없습니다.", max = 30)
        @Schema(description = "이메일 주소", example = "ensharp25@naver.com")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Size(message = "비밀번호는 8자 이상 12자 이하여야 합니다.", min = 8, max = 12)
        @Schema(description = "비밀번호", example = "ensharp25화이팅")
        String password,

        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        @Size(message = "닉네임은 2자 이상 10자 이하여야 합니다.", min = 2, max = 10)
        @Schema(description = "닉네임", example = "내이름은민재홍")
        String username,

        @NotBlank(message = "도로명주소는 필수 입력 값입니다")
        @Size(message = "도로명주소는 30자를 넘을 수 없습니다.", max = 50)
        @Schema(description = "도로명주소", example = "서울 광진구 능동로 209 (군자동, 세종대학교)")
        String streetAddress,

        // 상세주소는 빈값이여도 된다고 한다.. 대원피셜
        @Size(message = "상세주소는 30자를 넘을 수 없습니다.", max = 50)
        @Schema(description = "상세주소", example = "학생회관 530호")
        String detailAddress
) { }
