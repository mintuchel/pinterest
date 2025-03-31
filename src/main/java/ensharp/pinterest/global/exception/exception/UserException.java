package ensharp.pinterest.global.exception.exception;

import ensharp.pinterest.global.exception.errorcode.UserErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException {
    private UserErrorCode userErrorCode;
}
