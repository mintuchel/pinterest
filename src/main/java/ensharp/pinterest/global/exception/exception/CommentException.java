package ensharp.pinterest.global.exception.exception;

import ensharp.pinterest.global.exception.errorcode.CommentErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentException extends RuntimeException {
    private CommentErrorCode commentErrorCode;
}
