package com.hanium.diARy.diary.entity;

import com.hanium.diARy.plan.entity.Location;
import com.hanium.diARy.plan.entity.Plan;
import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
@Table(name = "travel_dest")
public class TravelDest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_dest_id")
    private int travelDestId;

    private String content;

    private String name;

    private String address;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "location_id", referencedColumnName = "location_id"),
            @JoinColumn(name = "plan_id", referencedColumnName = "plan_id")
    })
    private Location location;

    @ManyToOne
    @JoinColumn(name = "plan_id", insertable = false, updatable = false) // plan_id는 Plan 엔티티를 참조하는 데만 사용
    private Plan plan;

}
