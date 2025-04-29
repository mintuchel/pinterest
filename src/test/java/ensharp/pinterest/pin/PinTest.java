package ensharp.pinterest.pin;

import ensharp.pinterest.domain.pin.repository.PinRepository;
import ensharp.pinterest.domain.pin.service.PinService;
import ensharp.pinterest.domain.pin.service.S3Service;
import ensharp.pinterest.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    @DisplayName("comment 추가 성공")
    public void CreateCommentSuccess(){

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
