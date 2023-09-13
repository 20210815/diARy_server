package com.hanium.diARy.plan.entity;

import com.hanium.diARy.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "plan_take_in")
@IdClass(PlanTakeInId.class)
public class PlanTakeIn {
    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
