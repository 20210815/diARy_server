package com.hanium.diARy.diary.entity;

import com.hanium.diARy.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private int diaryId;

    private String title;

    @Column(name = "created_at")
    private Date createdAt;

    private String content;

    private int satisfaction;

    private String tag;

    @Column(name = "travel_period")
    private Date travelPeriod;

    @Column(name = "public")
    private boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}