package ensharp.pinterest.domain.comment.api;

import ensharp.pinterest.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public void createComment() {

    }

    @DeleteMapping("")
    public void deleteComment(){

    }

    @PatchMapping("")
    public void updateComment(){

    }
}
