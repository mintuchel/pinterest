package ensharp.pinterest.domain.pin.repository;

import ensharp.pinterest.domain.pin.entity.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PinRepository extends JpaRepository<Pin, UUID> {
    @Query(value = "SELECT * FROM pin WHERE title LIKE %:query% OR description LIKE %:query%", nativeQuery = true)
    List<Pin> findAllByQuery(@Param("query") String query);
}
