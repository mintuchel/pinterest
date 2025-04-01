package ensharp.pinterest.domain.pin.repository;

import ensharp.pinterest.domain.pin.entity.Pin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PinRepository extends JpaRepository<Pin, Integer> {
    Optional<Pin> findByS3Key(String s3Key);
}
