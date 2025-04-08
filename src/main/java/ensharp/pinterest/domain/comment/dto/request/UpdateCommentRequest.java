package ensharp.pinterest.domain.comment.dto.request;

public record UpdateCommentRequest(
    String commentId,
    String newContent
) { }
