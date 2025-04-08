package ensharp.pinterest.domain.favorite.api;

import ensharp.pinterest.domain.pin.dto.response.PinInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/favorites")
public class FavoriteController {
    @GetMapping("")
    public List<PinInfoResponse> getFavoritePins(){
        return null;
    }

    @PostMapping
    public void addFavoritePin(){

    }

    @DeleteMapping
    public void deleteFavoritePin(){

    }
}
