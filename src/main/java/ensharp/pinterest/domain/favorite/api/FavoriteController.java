package ensharp.pinterest.domain.favorite.api;

import ensharp.pinterest.domain.favorite.service.FavoriteService;
import ensharp.pinterest.domain.pin.dto.response.PinInfoResponse;
import ensharp.pinterest.domain.pin.dto.response.PinThumbnailResponse;
import ensharp.pinterest.global.security.model.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<List<PinThumbnailResponse>> getFavoritePins(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(favoriteService.getFavoritePins(jwtUserDetails.getId()));
    }

    @PostMapping("/{pinId}")
    public ResponseEntity<Void> addFavoritePin(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @PathVariable UUID pinId){
        favoriteService.createFavoritePin(jwtUserDetails.getId(), pinId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{pinId}")
    public ResponseEntity<Void> deleteFavoritePin(@AuthenticationPrincipal JwtUserDetails jwtUserDetail, @PathVariable UUID pinId){
        favoriteService.deleteFavoritePin(jwtUserDetail.getId(), pinId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
