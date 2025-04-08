package ensharp.pinterest.domain.pin.api;

import ensharp.pinterest.domain.comment.dto.response.CommentInfoResponse;
import ensharp.pinterest.domain.pin.dto.request.CreatePinRequest;
import ensharp.pinterest.domain.pin.dto.request.DeletePinRequest;
import ensharp.pinterest.domain.pin.dto.response.PinInfoResponse;
import ensharp.pinterest.domain.pin.dto.response.PinThumbnailResponse;
import ensharp.pinterest.domain.pin.service.PinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pin")
@Tag(name = "핀 API", description = "핀 업로드/수정/삭제")
public class PinController {

    private final PinService pinService;

    // ?query="" 이런 형식으로 처리됨
    // requestparam 없이 /api/v1/pin으로 보내도 정상적으로 동작
    @GetMapping("")
    @Operation(summary = "유저 쿼리에 기반한 Pin 썸네일 조회")
    public ResponseEntity<List<PinThumbnailResponse>> getPinThumbnails(@RequestParam(defaultValue = "") String query){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pinService.getPinThumbnails(query));
    }

    @GetMapping("/{pinId}")
    @Operation(summary = "특정 Pin 정보 조회")
    public ResponseEntity<PinInfoResponse> getPinInfo(@PathVariable String pinId){
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(pinService.getPin(pinId));
    }

    @PostMapping("/upload")
    @Operation(summary = "Pin 업로드")
    public ResponseEntity<String> createPin(@ModelAttribute CreatePinRequest createPinRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pinService.createPin(createPinRequest));
    }

    @DeleteMapping("")
    @Operation(summary = "Pin 삭제")
    public ResponseEntity<Void> deletePin(@Valid @RequestBody DeletePinRequest deletePinRequest) {
        pinService.deletePin(deletePinRequest);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("")
    @Operation(summary = "Pin 업데이트")
    public void updatePin() {

    }

    @GetMapping("/{pinId}/comments")
    @Operation(summary="특정 핀 댓글 조회")
    public ResponseEntity<CommentInfoResponse> getCommentsByPinId(@PathVariable String pinId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pinService.)
                .build();
    }
}
