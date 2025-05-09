package ensharp.pinterest.domain.comment.api;

import ensharp.pinterest.domain.comment.dto.request.CreateCommentRequest;
import ensharp.pinterest.domain.comment.dto.request.UpdateCommentRequest;
import ensharp.pinterest.domain.comment.dto.response.CommentInfoResponse;
import ensharp.pinterest.domain.comment.service.CommentService;
import ensharp.pinterest.global.security.model.JwtUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pins")
@RequiredArgsConstructor
@Tag(name = "Comment API", description = "Pin 댓글 관련")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{pinId}/comments")
    @Operation(summary = "특정 Pin 의 댓글 조회")
    // @CommonErrorResponses
    public ResponseEntity<List<CommentInfoResponse>> getCommentsByPinId(
            @Parameter(
                    description = "Pin ID",
                    example = "8dc5077a-33dc-4714-b529-39999b64a071"
            )
            @PathVariable String pinId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.getCommentsByPin(pinId));
    }

    @PostMapping("/{pinId}/comments")
    @Operation(summary = "특정 Pin 에 댓글 추가")
    // @CommonErrorResponses
    public ResponseEntity<Void> createComment(@AuthenticationPrincipal JwtUserDetails userDetails,
                                              @Parameter(
                                                      description = "Pin ID",
                                                      example = "8dc5077a-33dc-4714-b529-39999b64a071"
                                              )
                                              @PathVariable String pinId,
                                              @Valid @RequestBody CreateCommentRequest request) {

        commentService.createComment(userDetails.getId(), pinId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/comments/{commentId}")
    @Operation(summary = "특정 댓글 삭제")
    // @CommonErrorResponses
    public ResponseEntity<Void> deleteComment(@AuthenticationPrincipal JwtUserDetails userDetails,
                                              @Parameter(
                                                      description = "댓글 ID",
                                                      example = "817807d1-f494-4884-b881-0570a8638074"
                                              )
                                              @PathVariable String commentId) {

        commentService.deleteComment(userDetails.getId(), commentId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/comments/{commentId}")
    @Operation(summary = "특정 댓글 수정")
    // @CommonErrorResponses
    public ResponseEntity<Void> updateComment(@AuthenticationPrincipal JwtUserDetails userDetails,
                                              @Parameter(
                                                      description = "댓글 ID",
                                                      example = "817807d1-f494-4884-b881-0570a8638074"
                                              )
                                              @PathVariable String commentId,
                                              @Valid @RequestBody UpdateCommentRequest request) {

        commentService.updateComment(userDetails.getId(), commentId, request);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
