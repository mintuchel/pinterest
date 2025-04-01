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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    // S3에 업로드된 객체(파일)에 접근할 수 있는 전체 URL
    private String s3_url;

    // S3 내부에서 객체(파일)를 찾을 때 사용하는 경로(고유 식별자)
    private String s3_key;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;
}
