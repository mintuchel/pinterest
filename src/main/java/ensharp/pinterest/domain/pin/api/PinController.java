package ensharp.pinterest.domain.pin.api;

import ensharp.pinterest.domain.pin.dto.request.CreatePinRequest;
import ensharp.pinterest.domain.pin.service.PinService;
import ensharp.pinterest.domain.pin.service.S3Service;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pin")
@Tag(name = "핀 API", description = "핀 업로드/수정/삭제")
public class PinController {

    private final PinService pinService;

    @PostMapping("/upload")
    public void createPin(@ModelAttribute CreatePinRequest createPinRequest){
        pinService.createPin(createPinRequest);
    }

    @DeleteMapping("")
    public void deletePin(@RequestParam("id") int id) {

    }

    @PatchMapping("")
    public void updatePin() {

    }
}
