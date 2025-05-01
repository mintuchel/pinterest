package ensharp.pinterest.domain.comment.entity;

import ensharp.pinterest.domain.common.BaseEntity;
import ensharp.pinterest.domain.pin.entity.Pin;
import ensharp.pinterest.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

/**
 * 연관관계에 있는 User 와 Pin 에 지연로딩 적용
 * Comment 를 조회했을때는 프록시 객체로 조회되지만 연관객체 참조 시 추가 쿼리 나감 1+N -> 나중에 해결해보기
 */

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    private String authorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pin_id")
    private Pin pin;

    // 댓글 내용
    private String content;
}
