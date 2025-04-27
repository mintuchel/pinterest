package ensharp.pinterest.domain.comment.repository;

import ensharp.pinterest.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    // @Query 는 기본적으로 SELECT 전용임
    // UPDATE DELETE INSERT 와 같이 데이터를 변경하는 쿼리를 쓸때는 SELECT 가 아닌 DML 이라고 알려줘야함
    // 그래서 꼭 @Modifying 을 붙여줘야함

    // UPDATE DELETE 같은 변경 쿼리는 트랜잭션 안에서 실행되어야 DB에 반영됨
    // 그래서 @Transactional 을 붙여줘야함
    @Modifying
    @Transactional
    @Query(value = "UPDATE COMMENT SET content = :newContent WHERE id = :id", nativeQuery = true)
    void updateComment(UUID id, String newContent);
}
