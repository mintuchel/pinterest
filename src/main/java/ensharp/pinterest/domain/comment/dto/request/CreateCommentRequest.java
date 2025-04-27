package ensharp.pinterest.domain.comment.dto.request;

import java.util.UUID;

public record CreateCommentRequest(
        UUID pinId,
        String content
) { }
