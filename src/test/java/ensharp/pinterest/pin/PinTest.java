package ensharp.pinterest.pin;

import ensharp.pinterest.domain.pin.dto.request.CreatePinRequest;
import ensharp.pinterest.domain.pin.entity.Pin;
import ensharp.pinterest.domain.pin.repository.PinRepository;
import ensharp.pinterest.domain.pin.service.PinService;
import ensharp.pinterest.domain.pin.service.S3Service;
import ensharp.pinterest.domain.user.entity.User;
import ensharp.pinterest.domain.user.service.UserService;
import net.datafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PinTest {
    @InjectMocks
    private PinService pinService;

    @Mock
    private S3Service s3Service;

    @Mock
    private UserService userService;

    @Mock
    private PinRepository pinRepository;

    @Mock
    private User user;

    @Mock
    private CreatePinRequest createPinRequest;

    @Mock
    private Pin pin;

    private final Faker faker = new Faker();

    @Test
    @DisplayName("Pin S3 업로드 성공")
    public void CreatePinSuccess(){
        // given
        String userId = faker.internet().uuid();

        given(user.getUsername()).willReturn(faker.internet().username());
        given(userService.getUserById(userId)).willReturn(user);

        given(createPinRequest.getTitle()).willReturn("카즈하");
        given(createPinRequest.getDescription()).willReturn("카즈하 사진임");

        // when
        String s3Url = pinService.createPin(userId, createPinRequest);

        // then
        verify(pinRepository).save(pin);
        Assertions.assertThat(pin.getS3Url()).isEqualTo(s3Url);
        Assertions.assertThat(pin.getS3Key()).isNotBlank();
    }

    @Test
    @DisplayName("Pin 삭제 성공")
    public void DeletePinSuccess(){
        // given
        String userId = faker.internet().uuid();
        String pinId = faker.internet().uuid();

        given(user.getId()).willReturn(userId);
        given(pin.getUser()).willReturn(user);

        given(pinRepository.findById(pinId)).willReturn(Optional.of(pin));

        // when
        pinService.deletePin(userId, pinId);

        // then
        verify(pinRepository).delete(pin);
    }
}
