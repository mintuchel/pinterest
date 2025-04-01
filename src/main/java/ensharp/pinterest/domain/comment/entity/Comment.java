package ensharp.pinterest.domain.comment.entity;

import ensharp.pinterest.domain.common.BaseEntity;
import ensharp.pinterest.domain.pin.entity.Pin;
import ensharp.pinterest.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="pin_id")
    private Pin pin;

    // 댓글 내용
    private String content;
}
