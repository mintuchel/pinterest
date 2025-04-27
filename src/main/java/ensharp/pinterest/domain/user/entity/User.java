package ensharp.pinterest.domain.user.entity;

import ensharp.pinterest.domain.favorite.entity.Favorite;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    // 이메일
    @Column(nullable = false, unique = true, length = 30)
    private String email;

    // 비밀번호
    // encode 시 BCrypt로 60자 정도 길이의 해싱된 비번 반환. 여유있게 100자로 제한
    @Column(nullable = false, length = 100)
    private String password;

    // 닉네임
    @Column(nullable = false)
    private String username;

    // 도로명주소
    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    // 상세주소
    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Favorite> favorites = new ArrayList<>();
}