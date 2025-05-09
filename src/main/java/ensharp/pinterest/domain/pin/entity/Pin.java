package ensharp.pinterest.domain.pin.entity;

import ensharp.pinterest.domain.common.BaseEntity;
import ensharp.pinterest.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name="user_id", nullable=false)
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
}
