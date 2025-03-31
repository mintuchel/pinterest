package ensharp.pinterest.global.exception.exception;

import ensharp.pinterest.global.exception.errorcode.EmailErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailException extends RuntimeException {
    private EmailErrorCode emailErrorCode;
}