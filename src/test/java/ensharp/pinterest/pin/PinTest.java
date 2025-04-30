package ensharp.pinterest.pin;

import ensharp.pinterest.domain.pin.repository.PinRepository;
import ensharp.pinterest.domain.pin.service.PinService;
import ensharp.pinterest.domain.pin.service.S3Service;
import ensharp.pinterest.domain.user.service.UserService;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PinTest {
    @InjectMocks
    private PinService pinService;

    @Spy
    private S3Service s3Service;

    @Mock
    private UserService userService;

    @Mock
    private PinRepository pinRepository;

    private final Faker faker = new Faker();

    @Test
    @DisplayName("Pin S3 업로드 성공")
    public void UploadPinSuccess(){

    }
}
