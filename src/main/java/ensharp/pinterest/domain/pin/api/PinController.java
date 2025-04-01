package ensharp.pinterest.domain.pin.api;

import ensharp.pinterest.domain.pin.dto.request.CreatePinRequest;
import ensharp.pinterest.domain.pin.dto.request.DeletePinRequest;
import ensharp.pinterest.domain.pin.service.PinService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pin")
@Tag(name = "핀 API", description = "핀 업로드/수정/삭제")
public class PinController {

    private final PinService pinService;

    @GetMapping("/{s3Key}")
    public void getPin(@PathVariable String s3Key){

    }

    @PostMapping("/upload")
    public void createPin(@ModelAttribute CreatePinRequest createPinRequest){
        pinService.createPin(createPinRequest);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deletePin(@Valid @RequestBody DeletePinRequest deletePinRequest) {
        pinService.deletePin(deletePinRequest);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("")
    public void updatePin() {

    }
}
