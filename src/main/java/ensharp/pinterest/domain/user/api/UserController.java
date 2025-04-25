package ensharp.pinterest.domain.user.api;

import ensharp.pinterest.domain.user.dto.request.ChangePasswordRequest;
import ensharp.pinterest.domain.user.dto.request.CheckEmailRequest;
import ensharp.pinterest.domain.user.dto.request.LoginRequest;
import ensharp.pinterest.domain.user.dto.request.SignUpRequest;
import ensharp.pinterest.domain.user.dto.response.UserInfoResponse;
import ensharp.pinterest.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// @RestController가 @ResponseBody를 포함하므로 리턴되는 객체가 자동으로 JSON으로 변환되며,
// 응답의 Content-Type이 기본적으로 application/json으로 설정됨

// Controller 단에서 ResponseEntity 생성하는게 좋음
// 1. SOLID 원칙 준수: 단일 책임 원칙(SRP)에 따라, 서비스는 비즈니스 로직만, 컨트롤러는 HTTP 요청/응답 처리를 담당하는 게 깔끔.
// 2. 테스트 용이성: 서비스 단이 HTTP와 독립적이어서 단위 테스트가 쉬움.

// DTO에 @NotBlank를 적용했다면, 컨트롤러에서 @Valid 또는 @Validated를 사용해 검증을 트리거해야함

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "회원가입/로그인 API", description = "박대원 김시원 신혜연 화이팅")
public class UserController {
    private final UserService userService;

    @GetMapping("/{email}")
    @Operation(summary = "특정 유저 조회")
    public ResponseEntity<UserInfoResponse> getUserByEmail(@PathVariable String email) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserByEmail(email));
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<UUID> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.signUp(signUpRequest));
    }

    @PostMapping("/email-check")
    @Operation(summary = "이메일 중복 확인")
    public ResponseEntity<String> checkIfEmailAvailable(@Valid @RequestBody CheckEmailRequest checkEmailRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.checkIfEmailAvailable(checkEmailRequest));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.login(loginRequest).getUsername());
    }

    // RESTful 방식에서는 왜 noContent로 보내는 것이 적합하다고 하는 것일까
    // 200 + body에 메시지 담아서 보내줘도 되는데
    // 그냥 HttpStatus로 명확하게 얘기하는게 목표라서 그런 것일까??
    @PatchMapping("/password")
    @Operation(summary = "비밀번호 변경")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest);

        // HttpStatus 204로 반환
        return ResponseEntity.noContent().build();
    }
}