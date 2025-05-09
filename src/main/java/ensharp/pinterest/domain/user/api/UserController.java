package ensharp.pinterest.domain.user.api;

import ensharp.pinterest.domain.pin.dto.response.PinThumbnailResponse;
import ensharp.pinterest.domain.user.dto.response.UserInfoResponse;
import ensharp.pinterest.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "유저 정보 관련")
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    @Operation(summary = "특정 유저 정보 조회")
    public UserInfoResponse getUserInfo(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/{username}/pins")
    @Operation(summary = "특정 유저가 업로드한 Pin 조회")
    public List<PinThumbnailResponse> getPinsByUsername(@PathVariable String username) {
        return userService.getPinsByUsername(username);
    }
}
