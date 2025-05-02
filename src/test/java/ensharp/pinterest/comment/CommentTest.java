package ensharp.pinterest.comment;

import ensharp.pinterest.domain.comment.dto.request.CreateCommentRequest;
import ensharp.pinterest.domain.comment.repository.CommentRepository;
import ensharp.pinterest.domain.comment.service.CommentService;
import ensharp.pinterest.domain.pin.entity.Pin;
import ensharp.pinterest.domain.pin.service.PinService;
import ensharp.pinterest.domain.user.entity.User;
import ensharp.pinterest.domain.user.service.UserService;
import net.datafaker.Faker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.assertj.core.api.Assertions;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CommentTest {
    @InjectMocks
    private CommentService commentService;

    @Mock
    private PinService pinService;

    @Mock
    private UserService userService;

    @Mock
    private CommentRepository commentRepository;

    private final Faker faker = new Faker();

    @Mock
    private User user;

    @Mock
    private Pin pin;

    @Mock
    private CreateCommentRequest request;

    @Test
    @DisplayName("comment 추가 성공")
    public void CreateCommentSuccess(){
        // given
        String userId = faker.internet().uuid();
        String pinId = faker.internet().uuid();

        given(user.getUsername()).willReturn(faker.internet().username());
        given(request.content()).willReturn("content");
        given(userService.getUserById(userId)).willReturn(user);
        given(pinService.getPinById(pinId)).willReturn(pin);

        // when
        commentService.createComment(userId, pinId, request);

        // then
    }

    @Test
    @DisplayName("comment 삭제 성공")
    public void DeleteCommentSuccess(){

    }

    @Test
    @DisplayName("Pin 삭제 시 Cascade 성공")
    public void CommentCascadeSuccess(){

    }
}
