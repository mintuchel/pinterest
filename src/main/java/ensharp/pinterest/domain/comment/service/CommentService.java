package ensharp.pinterest.domain.comment.service;

import ensharp.pinterest.domain.comment.dto.request.CreateCommentRequest;
import ensharp.pinterest.domain.comment.dto.request.DeleteCommentRequest;
import ensharp.pinterest.domain.comment.dto.request.UpdateCommentRequest;
import ensharp.pinterest.domain.comment.entity.Comment;
import ensharp.pinterest.domain.comment.repository.CommentRepository;
import ensharp.pinterest.domain.pin.entity.Pin;
import ensharp.pinterest.domain.pin.service.PinService;
import ensharp.pinterest.domain.user.entity.User;
import ensharp.pinterest.domain.user.service.UserService;
import ensharp.pinterest.global.exception.errorcode.CommentErrorCode;
import ensharp.pinterest.global.exception.exception.CommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserService userService;
    private final PinService pinService;

    private final CommentRepository commentRepository;

    @Transactional
    public void createComment(CreateCommentRequest request) {

        User user = userService.getUserById(request.userId());
        Pin pin = pinService.getPinById(request.pinId());

        Comment comment = Comment.builder()
                .user(user)
                .pin(pin)
                .authorName(user.getUsername())
                .content(request.content())
                .build();

        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(DeleteCommentRequest request) {
        Comment comment = commentRepository.findById(request.commentId())
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        commentRepository.delete(comment);
    }

    @Transactional
    public void updateComment(UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(request.commentId())
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        commentRepository.updateComment(request.commentId(), request.newContent());
    }
}
