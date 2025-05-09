package ensharp.pinterest.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentErrorCode {
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다"),
    COMMENT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "자신이 작성한 댓글에 대해서만 수정/삭제 권한이 있습니다");

    private final HttpStatus httpStatus;
    private final String message;
}
