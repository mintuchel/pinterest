package ensharp.pinterest.domain.pin.api;

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

    private final S3Service s3Service;

    @PostMapping("/upload")
    public void createPin(
            @RequestPart("email") String email,
            @RequestPart("title") String title,
            @RequestPart("description") String description,
            @RequestPart("tag") String tag,
            @RequestPart("image") MultipartFile image) throws IOException {
        String imageUrl = s3Service.createPin(image);


    }

    @DeleteMapping("")
    public void deletePin(@RequestParam("id") int id) {

    }

    @PatchMapping("")
    public void updatePin() {

    }
}
