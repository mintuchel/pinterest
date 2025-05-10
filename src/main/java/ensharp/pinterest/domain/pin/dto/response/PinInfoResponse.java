package ensharp.pinterest.domain.pin.dto.response;

import ensharp.pinterest.domain.pin.entity.Pin;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "특정 Pin 정보 응답")
public record PinInfoResponse(
        @Schema(description = "Pin UUID", example = "8dc5077a-33dc-4714-b529-39999b64a071")
        String pinId,
        @Schema(description = "Pin S3 URL", example = "s3://ensharp-pinterest-bucket/29efc90e-b44c-4d8c-9817-f0d677a1da19-엔샵25.jpeg")
        String s3Url,
        @Schema(description = "Pin 제목", example = "엔샵사진")
        String title,
        @Schema(description = "Pin 설명", example = "수욜날 찍은거")
        String description,
        @Schema(description = "업로드한 유저 닉네임", example = "내이름은민재홍")
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
