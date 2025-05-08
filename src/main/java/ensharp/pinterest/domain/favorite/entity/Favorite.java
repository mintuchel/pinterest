package ensharp.pinterest.domain.favorite.entity;

import ensharp.pinterest.domain.pin.entity.Pin;
import ensharp.pinterest.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

/**
 * 이게 관심 Pin 등록인데, 이걸 굳이 복합키가 아니라 따로 id를 부여할 필요가 있을까?
 * 이 중간테이블은 사실 특정 User에 Favorite 조회랑 특정 User의 Favorite 중 특정 Pin 삭제 에 대한 쿼리만 나가.
 * 그럼 인덱스를 user_id 우선 + pin 그 후 우선순위로 하면 훨씬 인덱스 탈때 성능도 좋지 않아?
 * 그냥 아무 의미없는 id를 할 바에는?
 *
 * User Pin 복합키 걸어두고 -> Unique
 * 쿼리가 무조건 user_id + pin_id 조합으로 나가므로 PK로 인덱스를 걸어두면 성능적으로 유리함
 * "그냥 surrogate key(id)는 두고, 대신 (user_id, pin_id)에 unique 제약 + 복합 인덱스 걸자"
 *
 * @IdClass 또는 @EmbeddedIdClass 적용해야한다고 함
 */

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="pin_id", nullable = false)
    private Pin pin;
}
