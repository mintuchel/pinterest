package ensharp.pinterest.domain.comment.dto.response;

import java.time.LocalDateTime;

public record CommentInfoResponse(
    String username,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) { }
