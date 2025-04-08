package ensharp.pinterest.domain.comment.dto.request;

import lombok.Getter;

public record CreateCommentRequest(
        String userId,
        String pinId,
        String content
) { }
