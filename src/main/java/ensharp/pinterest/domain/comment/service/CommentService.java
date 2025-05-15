package ensharp.pinterest.domain.comment.service;

import ensharp.pinterest.domain.comment.dto.request.CreateCommentRequest;
import ensharp.pinterest.domain.comment.dto.request.UpdateCommentRequest;
import ensharp.pinterest.domain.comment.dto.response.CommentInfoResponse;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserService userService;
    private final PinService pinService;

    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<CommentInfoResponse> getCommentsByPin(String pinId){
        Pin pin = pinService.getPinById(pinId);

        return commentRepository.findByPinId(pin.getId())
                .stream()
                .map(CommentInfoResponse::from)
                .toList();
    }

    @Transactional
    public void createComment(String userId, String pinId, CreateCommentRequest request) {

        User user = userService.getUserById(userId);
        Pin pin = pinService.getPinById(pinId);

        Comment comment = Comment.builder()
                .user(user)
                .pin(pin)
                .authorName(user.getUsername())
                .content(request.content())
                .build();

        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(String userId, String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        if(!comment.getUser().getId().equals(userId)){
            throw new CommentException(CommentErrorCode.COMMENT_ACCESS_DENIED);
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public void updateComment(String userId, String commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        if(!comment.getUser().getId().equals(userId)){
            throw new CommentException(CommentErrorCode.COMMENT_ACCESS_DENIED);
        }

        commentRepository.updateComment(commentId, request.newContent());
    }
}
