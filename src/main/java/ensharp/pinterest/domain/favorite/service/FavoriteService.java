package ensharp.pinterest.domain.favorite.service;

import ensharp.pinterest.domain.favorite.repository.FavoriteRepository;
import ensharp.pinterest.domain.pin.dto.response.PinInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    @Transactional(readOnly = true)
    public List<PinInfoResponse> getFavoritePins(){
        return null;
    }

    @Transactional
    public void createFavoritePin(){

    }

    @Transactional
    public void deleteFavoritePin(){

    }
}
