package ensharp.pinterest.domain.pin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
public record UpdatePinRequest(

) { }
