package ensharp.pinterest.domain.comment.dto.response;

import ensharp.pinterest.domain.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "특정 댓글 정보 응답")
public record CommentInfoResponse(
        @Schema(description = "댓글 ID", example = "3334a57d-89c9-4140-8aa8-ecfefa2cafdf")
        String commentId,
        @Schema(description = "작성자", example = "내이름은민재홍")
        String authorName,
        @Schema(description = "댓글 내용", example = "풋살하고싶다")
        String content,
        @Schema(description = "최근 수정 일자", example = "2025-05-10T14:30:00")
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
