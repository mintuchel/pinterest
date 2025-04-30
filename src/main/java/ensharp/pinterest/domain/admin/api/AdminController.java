package ensharp.pinterest.domain.admin.api;

import ensharp.pinterest.domain.admin.service.AdminService;
import ensharp.pinterest.domain.user.dto.response.UserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "Admin API",
        description = "테스트 시 유용하게 사용하되, 다른 사람의 자원은 절대 삭제하지 마세요.\n\n" +
                "관리자 권한 남용 시 타 동기들에게 피해가 갈 수 있습니다.")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/users")
    @Operation(summary = "모든 유저 조회")
    public ResponseEntity<List<UserInfoResponse>> getAllUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adminService.getAllUsers());
    }

    @GetMapping("/users/{email}")
    @Operation(summary = "특정 유저 조회")
    public ResponseEntity<UserInfoResponse> getUserByEmail(@PathVariable String email) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adminService.getUserByEmail(email));
    }

    @DeleteMapping("/users/{email}")
    @Operation(summary = "특정 유저 삭제")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        adminService.deleteUserByEmail(email);

        // 왜 삭제 시 NO_CONTENT로 하는 것일까?
        // 삭제 성공 여부를 반환하면 안되는 것인가??
        // NO_CONTENT는 삭제 작업이 성공적으로 처리되었음을 나타내지만, 반환할 데이터가 없다는 의미 -> 라고 한다...
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/email-verification/{email}")
    @Operation(summary = "이메일 인증 기록 삭제")
    public ResponseEntity<Void> deleteEmail(@PathVariable String email) {
        adminService.deleteEmail(email);

        // 왜 삭제 시 NO_CONTENT로 하는 것일까?
        // 삭제 성공 여부를 반환하면 안되는 것인가??
        // NO_CONTENT는 삭제 작업이 성공적으로 처리되었음을 나타내지만, 반환할 데이터가 없다는 의미 -> 라고 한다...
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}