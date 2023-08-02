package com.hanium.diARy.plan.entity;

import lombok.Data;
import jakarta.persistence.*;
@Entity
@Data
@IdClass(LocationId.class)
public class Location {
    @Id
    @Column(name = "location_id")
    private int locationId;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "plan_id", referencedColumnName = "plan_id"),
            @JoinColumn(name = "user_id", referencedColumnName = "user_id") // user_id가 plan_id와 복합 기본 키
    })
    private Plan plan;

    private String name;

    private String address;

}

