package ensharp.pinterest.domain.pin.api;

import ensharp.pinterest.domain.pin.dto.request.CreatePinRequest;
import ensharp.pinterest.domain.pin.dto.request.UpdatePinRequest;
import ensharp.pinterest.domain.pin.dto.response.PinInfoResponse;
import ensharp.pinterest.domain.pin.dto.response.PinThumbnailResponse;
import ensharp.pinterest.domain.pin.entity.Pin;
import ensharp.pinterest.domain.pin.service.PinService;
import ensharp.pinterest.global.security.model.JwtUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pins")
@Tag(name = "Pin API", description = "Pin 조회 및 CRUD 관련")
public class PinController {

    private final PinService pinService;

    // 요청 파라미터 없이 /api/v1/pins 로 보내도 정상적으로 동작
    @GetMapping("")
    @Operation(summary = "유저 쿼리에 기반한 Pin 썸네일 조회")
    public ResponseEntity<List<PinThumbnailResponse>> getPinsByQuery(@RequestParam(defaultValue = "") String query){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pinService.getPinsByQuery(query));
    }

    @GetMapping("/{pinId}")
    @Operation(summary = "특정 Pin 정보 조회")
    public ResponseEntity<PinInfoResponse> getPinInfo(@PathVariable String pinId){

        Pin pin = pinService.getPinById(pinId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PinInfoResponse.from(pin));
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Pin 업로드")
    public ResponseEntity<String> createPin(@AuthenticationPrincipal JwtUserDetails userDetails, @ModelAttribute CreatePinRequest createPinRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pinService.createPin(userDetails.getId(), createPinRequest));
    }

    @DeleteMapping("/{pinId}")
    @Operation(summary = "Pin 삭제")
    public ResponseEntity<Void> deletePin(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable String pinId) {
        pinService.deletePin(userDetails.getId(), pinId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/{pinId}")
    @Operation(summary = "Pin 업데이트")
    public ResponseEntity<Void> updatePin(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable String pinId, @Valid @RequestBody UpdatePinRequest updatePinRequest) {
        pinService.updatePin(userDetails.getId(), pinId, updatePinRequest);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
