package ensharp.pinterest.domain.comment.dto.response;

import ensharp.pinterest.domain.comment.entity.Comment;

import java.time.LocalDateTime;

public record CommentInfoResponse(
    String commentId,
    String authorName,
    String content,
    LocalDateTime updatedAt
) {
    public static CommentInfoResponse from(Comment comment) {
        return new CommentInfoResponse(
                comment.getId(),
                comment.getAuthorName(),
                comment.getContent(),
                comment.getUpdatedAt()
        );
    }
}
