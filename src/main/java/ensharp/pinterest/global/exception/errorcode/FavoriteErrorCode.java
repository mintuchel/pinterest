package ensharp.pinterest.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FavoriteErrorCode {
    FAVORITE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 관심핀입니다"),
    // 409 반환. 같은 userId + pinId 조합의 즐겨찾기를 다시 등록하려고 할때 표준적인 선택의 Response Status
    FAVORITE_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 관심핀입니다");

    private final HttpStatus httpStatus;
    private final String message;
}
