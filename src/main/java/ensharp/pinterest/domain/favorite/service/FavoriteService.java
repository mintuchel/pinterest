package ensharp.pinterest.domain.favorite.service;

import ensharp.pinterest.domain.favorite.entity.Favorite;
import ensharp.pinterest.domain.favorite.repository.FavoriteRepository;
import ensharp.pinterest.domain.pin.dto.response.PinThumbnailResponse;
import ensharp.pinterest.domain.pin.entity.Pin;
import ensharp.pinterest.domain.pin.service.PinService;
import ensharp.pinterest.domain.user.entity.User;
import ensharp.pinterest.domain.user.service.UserService;
import ensharp.pinterest.global.exception.errorcode.FavoriteErrorCode;
import ensharp.pinterest.global.exception.exception.FavoriteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final PinService pinService;

    @Transactional(readOnly = true)
    public List<PinThumbnailResponse> getFavoritePins(String userId) {
        userService.existsById(userId);

        // map 이랑 stream 으로 처리하는게 중간에 필요한 임시 리스트를 사용하지 않고
        // 스트림 처리하면서 Favorite -> Pin -> DTO 로 바로 처리가능해서 메모리 효율 측면에서 좋음
        return favoriteRepository.findByUserId(userId).stream()
                .map(Favorite::getPin)
                .map(PinThumbnailResponse::from)
                .toList();
    }

    @Transactional
    public void createFavoritePin(String userId, String pinId) {

        if(favoriteRepository.existsByUserIdAndPinId(userId, pinId)) {
            throw new FavoriteException(FavoriteErrorCode.FAVORITE_ALREADY_EXISTS);
        }

        User user = userService.getUserById(userId);
        Pin pin = pinService.getPinById(pinId);

        Favorite favorite = Favorite.builder()
                .user(user)
                .pin(pin)
                .build();

        favoriteRepository.save(favorite);
    }

    @Transactional
    public void deleteFavoritePin(String userId, String pinId) {
        userService.existsById(userId);
        pinService.existsById(pinId);

        Favorite favorite = favoriteRepository.findByUserIdAndPinId(userId, pinId)
                .orElseThrow(() -> new FavoriteException(FavoriteErrorCode.FAVORITE_NOT_FOUND));

        favoriteRepository.delete(favorite);
    }
}
