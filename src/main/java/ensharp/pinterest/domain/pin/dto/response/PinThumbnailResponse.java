package ensharp.pinterest.domain.pin.dto.response;

import ensharp.pinterest.domain.pin.entity.Pin;

public record PinThumbnailResponse(
        String pinId,
        String s3Url
) {
    public static PinThumbnailResponse from(Pin pin){
        return new PinThumbnailResponse(
                pin.getId(),
                pin.getS3Url()
        );
    }
}
