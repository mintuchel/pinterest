package ensharp.pinterest.domain.comment.service;

import ensharp.pinterest.domain.comment.dto.request.CreateCommentRequest;
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
    public void createComment(String userId, CreateCommentRequest request) {

        User user = userService.getUserById(userId);
        Pin pin = pinService.getPinById(request.pinId());

        Comment comment = Comment.builder()
                .user(user)
                .pin(pin)
                .authorName(user.getUsername())
                .content(request.content())
                .build();

        // 연관관계 편의 메서드
        pin.addComment(comment);

        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(String userId, String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        if(comment.getUser().getId()!=userId){
            throw new CommentException(CommentErrorCode.COMMENT_ACCESS_DENIED);
        }

        // 연관관계 편의 메서드
        Pin pin = comment.getPin();
        pin.removeComment(comment);

        commentRepository.delete(comment);
    }

    @Transactional
    public void updateComment(String userId, String commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        if(comment.getUser().getId()!=userId){
            throw new CommentException(CommentErrorCode.COMMENT_ACCESS_DENIED);
        }

        // 연관관계 편의 메서드
        Pin pin = comment.getPin();
        pin.updateComment(comment);

        commentRepository.updateComment(commentId, request.newContent());
    }
}
