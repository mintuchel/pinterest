package ensharp.pinterest.global.exception.exception;

import ensharp.pinterest.global.exception.errorcode.PinErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PinException extends RuntimeException {
    private PinErrorCode pinErrorCode;
}
