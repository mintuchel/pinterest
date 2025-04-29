package ensharp.pinterest.domain.auth.api;

import ensharp.pinterest.domain.auth.dto.ChangePasswordRequest;
import ensharp.pinterest.domain.auth.dto.CheckEmailRequest;
import ensharp.pinterest.domain.auth.service.AuthService;
import ensharp.pinterest.domain.auth.dto.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "회원가입 및 인증/인가 관련")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/email-check")
    @Operation(summary = "이메일 중복 확인")
    public ResponseEntity<String> checkIfEmailAvailable(@Valid @RequestBody CheckEmailRequest checkEmailRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.checkIfEmailAvailable(checkEmailRequest));
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.signUp(signUpRequest));
    }

    // RESTful 방식에서는 왜 noContent로 보내는 것이 적합하다고 하는 것일까
    // 200 + body에 메시지 담아서 보내줘도 되는데
    // 그냥 HttpStatus로 명확하게 얘기하는게 목표라서 그런 것일까?? 함 생각해보기
    @PatchMapping("/password")
    @Operation(summary = "비밀번호 변경")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        authService.changePassword(changePasswordRequest);

        // HttpStatus 204로 반환
        return ResponseEntity.noContent().build();
    }
}
