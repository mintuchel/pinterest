package ensharp.pinterest.domain.comment.dto.response;

import ensharp.pinterest.domain.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "특정 Comment 정보 응답")
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
