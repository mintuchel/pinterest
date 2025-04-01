package ensharp.pinterest.global.exception.handler;

import ensharp.pinterest.global.exception.exception.EmailException;
import ensharp.pinterest.global.exception.exception.PinException;
import ensharp.pinterest.global.exception.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = UserException.class)
    protected ResponseEntity<String> handleUserException(UserException e) {
        return ResponseEntity
                .status(e.getUserErrorCode().getHttpStatus())
                .body(e.getUserErrorCode().getMessage());
    }

    @ExceptionHandler(value = EmailException.class)
    protected ResponseEntity<String> handleEmailException(EmailException e) {
        return ResponseEntity
                .status(e.getEmailErrorCode().getHttpStatus())
                .body(e.getEmailErrorCode().getMessage());
    }

    @ExceptionHandler(value = PinException.class)
    protected ResponseEntity<String> handlePinException(PinException e) {
        return ResponseEntity
                .status(e.getPinErrorCode().getHttpStatus())
                .body(e.getPinErrorCode().getMessage());
    }

    // Jakarta Validation 관련 에러 처리 (DTO 형식 관련)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}
