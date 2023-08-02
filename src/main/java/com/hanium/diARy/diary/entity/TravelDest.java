package com.hanium.diARy.diary.entity;

import com.hanium.diARy.plan.entity.Location;
import com.hanium.diARy.plan.entity.Plan;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "travel_dest")
public class TravelDest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_dest_id")
    private Long travelDestId;

    @ManyToOne
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Column(name = "name", length = 10)
    private String name;

    @Column(name = "address", length = 100)
    private String address;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

}
