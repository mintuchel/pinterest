package ensharp.pinterest.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// JPA에서는 PrePersist, PreUpdate 등 Entity에 LifeCytcle과 관련된 이벤트들이 존재하는데 해당 이벤트를 Listen 해주는것
// 이걸 사용하려면 Main 에 @EnableJpaAuditing 이거 붙여줘야함
@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class BaseEntity2 {

    // @CreatedDate : @PrePersist 와 비슷한 기능. Entity가 생성되어 저장될때 시간이 자동으로 저장됨
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // @LaseModifiedDate : @PreUpdate 와 비슷한 기능으로, 조회한 Entity의 값을 변경할 때 시간이 자동으로 저장
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
