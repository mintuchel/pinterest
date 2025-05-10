package ensharp.pinterest.domain.pin.dto.response;

import ensharp.pinterest.domain.pin.entity.Pin;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "특정 Pin 세부정보 응답")
public record PinInfoResponse(
        @Schema(description = "Pin UUID", example = "29efc90e-b44c-4d8c-9817-f0d677a1da19")
        String pinId,
        @Schema(description = "Pin S3 URL", example = "s3://ensharp-pinterest-bucket/29efc90e-b44c-4d8c-9817-f0d677a1da19-엔샵25.jpeg")
        String s3Url,
        @Schema(description = "Pin 제목", example = "엔샵사진")
        String title,
        @Schema(description = "Pin 설명", example = "엔샵 여름 MT")
        String description,
        @Schema(description = "업로더 닉네임", example = "mintuchel")
        String authorUserName
) {
    public static PinInfoResponse from(Pin pin){
        return new PinInfoResponse(
            pin.getId(),
            pin.getS3Url(),
            pin.getTitle(),
            pin.getDescription(),
            pin.getAuthorName()
        );
    }
}
