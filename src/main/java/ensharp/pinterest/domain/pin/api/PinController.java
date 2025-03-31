package ensharp.pinterest.domain.pin.api;

import ensharp.pinterest.domain.pin.service.PinService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pin")
@Tag(name = "핀 API", description = "핀 업로드/수정/삭제")
public class PinController {

    private final PinService pinService;

    @PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file) {

    }

    @DeleteMapping("")
    public void delete(@RequestParam("id") int id) {

    }

    @PatchMapping("")
    public void patch() {

    }
}
