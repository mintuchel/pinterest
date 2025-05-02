package ensharp.pinterest.domain.favorite.api;

import ensharp.pinterest.domain.favorite.service.FavoriteService;
import ensharp.pinterest.domain.pin.dto.response.PinThumbnailResponse;
import ensharp.pinterest.global.security.model.JwtUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
@Tag(name = "Favorite API", description = "관심핀 관련")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    @Operation(summary = "특정 유저의 관심핀 조회")
    public ResponseEntity<List<PinThumbnailResponse>> getFavoritePins(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(favoriteService.getFavoritePins(jwtUserDetails.getId()));
    }

    @PostMapping("/{pinId}")
    @Operation(summary = "특정 유저의 관심핀 추가")
    public ResponseEntity<Void> addFavoritePin(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @PathVariable String pinId){
        favoriteService.createFavoritePin(jwtUserDetails.getId(), pinId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{pinId}")
    @Operation(summary = "특정 유저의 관심핀 삭제")
    public ResponseEntity<Void> deleteFavoritePin(@AuthenticationPrincipal JwtUserDetails jwtUserDetail, @PathVariable String pinId){
        favoriteService.deleteFavoritePin(jwtUserDetail.getId(), pinId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
