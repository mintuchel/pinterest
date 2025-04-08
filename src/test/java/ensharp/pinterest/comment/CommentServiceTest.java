package ensharp.pinterest.comment;


import ensharp.pinterest.domain.comment.dto.request.CreateCommentRequest;
import ensharp.pinterest.domain.comment.dto.request.DeleteCommentRequest;
import ensharp.pinterest.domain.comment.dto.request.UpdateCommentRequest;
import ensharp.pinterest.domain.comment.entity.Comment;
import ensharp.pinterest.domain.comment.repository.CommentRepository;
import ensharp.pinterest.domain.comment.service.CommentService;
import ensharp.pinterest.domain.pin.entity.Pin;
import ensharp.pinterest.domain.pin.service.PinService;
import ensharp.pinterest.domain.user.service.UserService;
import ensharp.pinterest.domain.user.entity.User;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PinService pinService;

    @Mock
    private UserService userService;

    private final Faker faker = new Faker();

    @Mock
    private Comment comment;

    @Mock
    private User user;

    @Mock
    private Pin pin;

    @Mock
    private CreateCommentRequest createCommentRequest;
    @Mock
    private DeleteCommentRequest deleteCommentRequest;
    @Mock
    private UpdateCommentRequest updateCommentRequest;

    @Test
    public void addCommentSuccessTest(){
        // given
        String userId = faker.internet().uuid();
        String pinId = faker.internet().uuid();

        given(createCommentRequest.userId()).willReturn(userId);
        given(createCommentRequest.pinId()).willReturn(pinId);
        given(userService.getUserById(userId)).willReturn(user);
        given(pinService.getPinById(pinId)).willReturn(pin);

        // when
        commentService.createComment(createCommentRequest);

        // then
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    public void deleteCommentSuccessTest(){
        // given
        String commentId = faker.internet().uuid();

        given(deleteCommentRequest.commentId()).willReturn(commentId);
        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

        // when
        commentService.deleteComment(deleteCommentRequest);

        // then
        verify(commentRepository).delete(any(Comment.class));
    }

    @Test
    public void updateCommentSuccessTest(){
        String commentId = faker.internet().uuid();
        String newContent = faker.lorem().sentence();

        given(updateCommentRequest.commentId()).willReturn(commentId);
        given(updateCommentRequest.newContent()).willReturn(newContent);
        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

        // when
        commentService.updateComment(updateCommentRequest);

        // then
        verify(commentRepository).updateComment(commentId, newContent);
    }
}
