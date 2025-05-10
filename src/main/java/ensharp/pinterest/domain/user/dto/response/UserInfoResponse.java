package ensharp.pinterest.domain.user.dto.response;

// responsedto에 정적 팩토리 메서드 사용
// record 내부에 static으로 생성자같은 함수를 집어넣는 디자인 패턴
// new 또는 builder를 사용하지 않는 패턴이다
// 나도 지금은 개발하느라 왜 이런걸 쓰는지 이해는 안간다...

import ensharp.pinterest.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

// of 랑 from 은 정적 팩토리 메서드에서 자주 쓰이는 네이밍 컨벤션이라고 한다
// of = 명시적인 값들을 기반으로 객체 생성
// from = 객체를 기반으로 생성
@Schema(description = "현재 로그인한 유저 정보 응답")
public record UserInfoResponse (
        @Schema(description = "이메일 주소", example = "ensharp25@naver.com")
        String email,
        @Schema(description = "닉네임", example = "내이름은민재홍")
        String username,
        @Schema(description = "도로명주소", example = "서울 광진구 능동로 209 (군자동, 세종대학교)")
        String streetAddress,
        @Schema(description = "상세주소", example = "학생회관 530호")
        String detailAddress
) {
    public static UserInfoResponse from(User user) {
        return new UserInfoResponse(
                user.getEmail(),
                user.getUsername(),
                user.getStreetAddress(),
                user.getDetailAddress()
        );
    }
}