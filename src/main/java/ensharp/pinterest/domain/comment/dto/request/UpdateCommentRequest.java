package ensharp.pinterest.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdateCommentRequest(
        @NotBlank(message = "댓글은 필수 입력 값입니다.")
        @Schema(description = "업데이트 될 댓글 내용", example = "김고은 너무 이쁘다")
        String newContent
) { }
