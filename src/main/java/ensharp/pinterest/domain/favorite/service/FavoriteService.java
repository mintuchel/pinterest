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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final PinService pinService;

    @Transactional(readOnly = true)
    public List<PinThumbnailResponse> getFavoritePins(String userId) {
        User user = userService.getUserById(userId);

        List<Favorite> favorites = user.getFavorites();

        List<Pin> pinList = new ArrayList<Pin>();
        for (Favorite favorite : favorites) {
            pinList.add(favorite.getPin());
        }

        return pinList.stream()
                .map(PinThumbnailResponse::from)
                .toList();
    }

    @Transactional
    public void createFavoritePin(String userId, String pinId) {
        User user = userService.getUserById(userId);
        Pin pin = pinService.getPinById(pinId);

        Favorite favorite = Favorite.builder()
                .user(user)
                .pin(pin)
                .build();

        user.getFavorites().add(favorite); // 연관관계 편의 메서드
        favoriteRepository.save(favorite);
    }

    @Transactional
    public void deleteFavoritePin(String userId, String pinId) {
        User user = userService.getUserById(userId);
        Pin pin = pinService.getPinById(pinId);

        Favorite favorite = favoriteRepository.findByUserIdAndPinId(userId, pinId)
                .orElseThrow(() -> new FavoriteException(FavoriteErrorCode.FAVORITE_NOT_FOUND));

        user.getFavorites().remove(favorite); // 연관관계 편의 메서드
        favoriteRepository.delete(favorite);
    }
}
