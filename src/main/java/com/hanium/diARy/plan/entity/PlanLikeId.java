package com.hanium.diARy.plan.entity;

import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PlanLikeId implements Serializable {
    private Long plan;
    private Long user;

}
