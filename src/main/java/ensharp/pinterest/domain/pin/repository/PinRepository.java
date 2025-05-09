package ensharp.pinterest.domain.pin.repository;

import ensharp.pinterest.domain.pin.entity.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PinRepository extends JpaRepository<Pin, String> {
    @Query(value = "SELECT * FROM pin WHERE title LIKE %:query% OR description LIKE %:query%", nativeQuery = true)
    List<Pin> findAllByQuery(@Param("query") String query);

    @Query(value = "SELECT * FROM pin WHERE authorName = :username", nativeQuery = true)
    List<Pin> findAllByUserName(@Param("username") String username);

    @Modifying
    @Query(value = "UPDATE pin SET title = :title, description = :description WHERE id = :pinId", nativeQuery = true)
    void updatePin(@Param("pinId") String pinId, @Param("title") String title, @Param("description") String description);
}
