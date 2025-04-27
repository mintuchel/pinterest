package ensharp.pinterest.domain.pin.dto.response;

import ensharp.pinterest.domain.pin.entity.Pin;

import java.util.UUID;

public record PinInfoResponse(
        UUID pinId,
        String s3Url,
        String title,
        String description,
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
