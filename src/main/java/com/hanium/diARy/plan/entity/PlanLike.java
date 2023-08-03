package com.hanium.diARy.plan.entity;

import com.hanium.diARy.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "plan_like")
@IdClass(PlanLikeId.class) // 복합 기본 키 클래스를 지정
public class PlanLike {
    @Id
    @ManyToOne(fetch = FetchType.LAZY) // Plan과의 관계에서 지연 로딩으로 설정
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Id
    @ManyToOne(fetch = FetchType.LAZY) // User와의 관계에서 지연 로딩으로 설정
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
