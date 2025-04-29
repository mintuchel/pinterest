package ensharp.pinterest.domain.comment.api;

import ensharp.pinterest.domain.comment.dto.request.CreateCommentRequest;
import ensharp.pinterest.domain.comment.dto.request.UpdateCommentRequest;
import ensharp.pinterest.domain.comment.service.CommentService;
import ensharp.pinterest.global.security.model.JwtUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(name = "Comment API", description = "Pin 댓글 관련")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    @Operation(summary = "댓글 추가")
    public ResponseEntity<Void> createComment(@AuthenticationPrincipal JwtUserDetails userDetails, @Valid @RequestBody CreateCommentRequest request) {

        commentService.createComment(userDetails.getId(), request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable String commentId) {

        commentService.deleteComment(userDetails.getId(), commentId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/{commentId}")
    @Operation(summary = "댓글 수정")
    public ResponseEntity<Void> updateComment(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable String commentId, @Valid @RequestBody UpdateCommentRequest request) {

        commentService.updateComment(userDetails.getId(), commentId, request);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
