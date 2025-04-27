package ensharp.pinterest.domain.favorite.repository;

import ensharp.pinterest.domain.favorite.entity.Favorite;
import ensharp.pinterest.domain.pin.entity.Pin;
import ensharp.pinterest.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    List<Favorite> findByUser(User user);

    @Query(value = "SELECT pin_id FROM favorite WHERE user_id = :userId", nativeQuery = true)
    List<UUID> findPinIdByUserId(@Param("userid") UUID userId);

    Optional<Favorite> findByUserAndPin(User user, Pin pin);
}
