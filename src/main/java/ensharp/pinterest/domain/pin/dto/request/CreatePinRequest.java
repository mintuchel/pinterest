package ensharp.pinterest.domain.pin.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class CreatePinRequest {
    private String email;
    private String title;
    private String description;

    private MultipartFile image;
}
