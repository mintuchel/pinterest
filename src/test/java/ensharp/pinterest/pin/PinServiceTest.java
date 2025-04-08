package ensharp.pinterest.pin;

import ensharp.pinterest.domain.comment.dto.response.CommentInfoResponse;
import ensharp.pinterest.domain.comment.entity.Comment;
import ensharp.pinterest.domain.pin.entity.Pin;
import ensharp.pinterest.domain.pin.repository.PinRepository;
import ensharp.pinterest.domain.pin.service.PinService;
import ensharp.pinterest.domain.pin.service.S3Service;
import ensharp.pinterest.domain.user.service.UserService;
import net.datafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PinServiceTest {
    @InjectMocks
    private PinService pinService;

    @Mock
    private S3Service s3Service;
    @Mock
    private UserService userService;
    @Mock
    private PinRepository pinRepository;

    @Mock
    private Pin pin;

    private final Faker faker = new Faker();

    @BeforeEach
    public void pinSetUp(){

    }

    @Test
    public void getCommentsSuccessTest(){
        // given
        String pinId = faker.internet().uuid();

        // 더미 Comment 객체 생성
        Comment comment1 = Comment.builder()
                .id(faker.internet().uuid())
                .authorName(faker.name().firstName())
                .content(faker.lorem().sentence())
                .build();

        Comment comment2 = Comment.builder()
                .id(faker.internet().uuid())
                .authorName(faker.name().firstName())
                .content(faker.lorem().sentence())
                .build();

        List<Comment> commentList = List.of(comment1, comment2);

        given(pinRepository.findById(pinId)).willReturn(Optional.of(pin));
        given(pin.getComments()).willReturn(commentList);

        // when
        List<CommentInfoResponse> result = pinService.getCommentsByPinId(pinId);

        // then
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0).authorName()).isEqualTo(comment1.getAuthorName());
        Assertions.assertThat(result.get(1).content()).isEqualTo(comment2.getContent());
    }

    @Test
    public void getPinByIdSuccessTest(){
        // given

        // when

        // then
    }

    @Test
    public void getPinThumbnailSuccessTest(){

    }

    @Test
    public void createPinSuccessTest(){

    }
}
