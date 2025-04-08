package ensharp.pinterest.domain.comment.api;

import ensharp.pinterest.domain.comment.dto.request.CreateCommentRequest;
import ensharp.pinterest.domain.comment.dto.request.DeleteCommentRequest;
import ensharp.pinterest.domain.comment.dto.request.UpdateCommentRequest;
import ensharp.pinterest.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<Void> createComment(CreateCommentRequest request) {

        commentService.createComment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteComment(DeleteCommentRequest request) {

        commentService.deleteComment(request);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("")
    public ResponseEntity<Void> updateComment(UpdateCommentRequest request) {

        commentService.updateComment(request);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
