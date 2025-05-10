package ensharp.pinterest.domain.pin.dto.response;

import ensharp.pinterest.domain.pin.entity.Pin;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "특정 Pin 썸네일 응답")
public record PinThumbnailResponse(
        @Schema(description = "Pin UUID", example = "29efc90e-b44c-4d8c-9817-f0d677a1da19")
        String pinId,
        @Schema(description = "Pin S3 URL", example = "s3://ensharp-pinterest-bucket/29efc90e-b44c-4d8c-9817-f0d677a1da19-칼국수.jpeg")
        String s3Url
) {
    public static PinThumbnailResponse from(Pin pin){
        return new PinThumbnailResponse(
                pin.getId(),
                pin.getS3Url()
        );
    }
}
