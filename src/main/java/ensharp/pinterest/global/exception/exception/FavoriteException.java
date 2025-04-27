package ensharp.pinterest.global.exception.exception;

import ensharp.pinterest.global.exception.errorcode.FavoriteErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FavoriteException extends RuntimeException {
    private FavoriteErrorCode favoriteErrorCode;
}
