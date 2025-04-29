package ensharp.pinterest.domain.pin.api;

import ensharp.pinterest.domain.comment.dto.response.CommentInfoResponse;
import ensharp.pinterest.domain.pin.dto.request.CreatePinRequest;
import ensharp.pinterest.domain.pin.dto.request.UpdatePinRequest;
import ensharp.pinterest.domain.pin.dto.response.PinInfoResponse;
import ensharp.pinterest.domain.pin.dto.response.PinThumbnailResponse;
import ensharp.pinterest.domain.pin.service.PinService;
import ensharp.pinterest.global.security.model.JwtUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pins")
@Tag(name = "Pin API", description = "Pin 조회 및 CRUD 관련")
public class PinController {

    private final PinService pinService;

    // ?query="" 이런 형식으로 처리됨
    // requestparam 없이 /api/v1/pin으로 보내도 정상적으로 동작
    @GetMapping("")
    @Operation(summary = "유저 쿼리에 기반한 Pin 썸네일 조회")
    public ResponseEntity<List<PinThumbnailResponse>> getPinsByQuery(@RequestParam(defaultValue = "") String query){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pinService.getPinsByQuery(query));
    }

    @GetMapping("/{pinId}")
    @Operation(summary = "특정 Pin 정보 조회")
    public ResponseEntity<PinInfoResponse> getPinInfo(@PathVariable UUID pinId){
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(pinService.getPin(pinId));
    }

    @PostMapping("/upload")
    @Operation(summary = "Pin 업로드")
    public ResponseEntity<String> createPin(@AuthenticationPrincipal JwtUserDetails userDetails, @ModelAttribute CreatePinRequest createPinRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pinService.createPin(userDetails.getId(), createPinRequest));
    }

    @DeleteMapping("/{pinId}")
    @Operation(summary = "Pin 삭제")
    public ResponseEntity<Void> deletePin(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable UUID pinId) {
        pinService.deletePin(userDetails.getId(), pinId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/{pinId}")
    @Operation(summary = "Pin 업데이트")
    public ResponseEntity<Void> updatePin(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable UUID pinId, @Valid @RequestBody UpdatePinRequest updatePinRequest) {
        pinService.updatePin(userDetails.getId(), pinId, updatePinRequest);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{pinId}/comments")
    @Operation(summary="특정 Pin 댓글 조회")
    public ResponseEntity<List<CommentInfoResponse>> getCommentsByPinId(@PathVariable UUID pinId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pinService.getCommentsByPinId(pinId));
    }
}
