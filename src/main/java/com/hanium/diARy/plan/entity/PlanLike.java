package com.hanium.diARy.plan.entity;

import com.hanium.diARy.user.entity.User;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "plan_like")
@IdClass(PlanLikeId.class) // 복합 기본 키 클래스를 지정
public class PlanLike {
    @Id
    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
