package ensharp.pinterest.domain.comment.api;

import ensharp.pinterest.domain.comment.dto.request.CreateCommentRequest;
import ensharp.pinterest.domain.comment.dto.request.UpdateCommentRequest;
import ensharp.pinterest.domain.comment.service.CommentService;
import ensharp.pinterest.global.security.model.JwtUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<Void> createComment(@AuthenticationPrincipal JwtUserDetails userDetails, CreateCommentRequest request) {

        commentService.createComment(userDetails.getId(), request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable UUID commentId) {

        commentService.deleteComment(userDetails.getId(), commentId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable UUID commentId, @Valid @RequestBody UpdateCommentRequest request) {

        commentService.updateComment(userDetails.getId(), commentId, request);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
