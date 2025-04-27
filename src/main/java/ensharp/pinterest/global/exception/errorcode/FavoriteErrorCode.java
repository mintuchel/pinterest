package ensharp.pinterest.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FavoriteErrorCode {
    FAVORITE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 관심핀 입니다");

    private final HttpStatus httpStatus;
    private final String message;
}
