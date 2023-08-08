package com.hanium.diARy.diary.entity;

import com.hanium.diARy.plan.entity.Location;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "diary_location")
public class DiaryLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_Location_id")
    private Long diaryLocationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Column(name = "name", length = 10)
    private String name;

    @Column(name = "address", length = 100)
    private String address;

}
