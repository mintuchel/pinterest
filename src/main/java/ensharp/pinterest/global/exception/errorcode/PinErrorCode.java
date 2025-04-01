package ensharp.pinterest.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PinErrorCode {

    IMAGE_NOT_SENT(HttpStatus.BAD_REQUEST, "이미지는 필수입니다"),
    PIN_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 Pin 입니다");

    private final HttpStatus httpStatus;
    private final String message;
}
