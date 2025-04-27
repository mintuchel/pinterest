package ensharp.pinterest.domain.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// 순수 JPA를 이용한 BaseEntity 구현

// @MappedSuperclass
// 얘는 클래스 명만 Entity 이지, 실제 Entity 클래스가 아님
// Entity 클래스는 Entity 클래스들끼리만 상속 받을 수 있는데, Entity 클래스에서 일반 클래스를 상속받기 위해서는 이 어노테이션을 작성해줘야함
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // @PrePersist
    // EntityManager 가 persist() 를 통해 영속화 하기 전에 실행되는 메서드
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    // @PreUpdate
    // 변경감지 기능을 통해 update 쿼리가 실행되었을때 실행되는 메서드
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}