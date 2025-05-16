package ensharp.pinterest.domain.comment.repository;

import ensharp.pinterest.domain.comment.entity.Comment;
import ensharp.pinterest.domain.pin.entity.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE comment SET content = :newContent WHERE id = :id", nativeQuery = true)
    void updateComment(String id, String newContent);

    @Query(value = "SELECT * from comment WHERE pin_id = :pinId ORDER BY created_at", nativeQuery = true)
    List<Comment> findByPinId(@Param("pinId") String pinId);
}
