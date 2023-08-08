package com.hanium.diARy.diary.entity;

import lombok.Data;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Data
@Entity
@Table(name = "diary_location")
public class DiaryLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_location_id")
    private Long diaryLocationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @Column(name = "date")
    private Date date;

    @Column(name = "time_start")
    private Time timeStart;

    @Column(name = "time_end")
    private Time timeEnd;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Column(name = "name", length = 10)
    private String name;

    @Column(name = "address", length = 100)
    private String address;

}
