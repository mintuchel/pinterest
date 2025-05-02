package ensharp.pinterest.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequest(
        @NotBlank(message = "댓글은 필수 입력 값입니다.")
        @Schema(description = "댓글 내용", example = "김고은 이쁘다")
        String content
) { }
