package ensharp.pinterest.domain.pin.entity;

import ensharp.pinterest.domain.comment.entity.Comment;
import ensharp.pinterest.domain.common.BaseEntity;
import ensharp.pinterest.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Pin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false)
    private String authorName;

    // S3에 업로드된 객체(파일)에 접근할 수 있는 전체 URL
    @Column(nullable = false)
    private String s3Url;

    // S3 내부에서 객체(파일)를 찾을 때 사용하는 경로(고유 식별자)
    @Column(nullable = false)
    private String s3Key;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;


    // 양방향 했을때에서 단방향으로 바꿈
    // 일대다 단방향에서 일쪽을 주인으로 두는게 이 관계에서는 훨씬 편할거 같은데..
//    @OneToMany(mappedBy = "pin", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @Builder.Default
//    private List<Comment> comments = new ArrayList<>();
//
//    public void addComment(Comment comment) {
//        comments.add(comment);
//    }
//
//    public void removeComment(Comment comment) {
//        comments.remove(comment);
//    }
//
//    public void updateComment(Comment comment) {
//        removeComment(comment);
//        addComment(comment);
//    }
}
