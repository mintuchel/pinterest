package ensharp.pinterest.domain.pin.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class CreatePinRequest {
    private String email;
    private String title;
    private String description;

    private MultipartFile image;
}
