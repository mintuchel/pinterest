package ensharp.pinterest.domain.pin.dto.response;

import ensharp.pinterest.domain.pin.entity.Pin;

import java.util.UUID;

public record PinThumbnailResponse(
        UUID pinId,
        String s3Url
) {
    public static PinThumbnailResponse from(Pin pin){
        return new PinThumbnailResponse(
                pin.getId(),
                pin.getS3Url()
        );
    }
}
