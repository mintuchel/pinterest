package ensharp.pinterest.domain.favorite.repository;

import ensharp.pinterest.domain.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    @Query(value = "SELECT * from favorite WHERE user_id = :userId AND pin_id = :pinId", nativeQuery = true)
    Optional<Favorite> findByUserIdAndPinId(@Param("userId") String userId, @Param("pinId") String pinId);
}
