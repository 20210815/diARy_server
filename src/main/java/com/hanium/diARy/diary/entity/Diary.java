package com.hanium.diARy.diary.entity;

import com.hanium.diARy.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;

@Data
@Entity
@Table(name = "Diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long diaryId;

    @Column(name = "title", nullable = false, length = 10)
    private String title;

    @Column(name = "create_at", nullable = false)
    private Date createdAt;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Column(name = "satisfaction", nullable = false)
    private int satisfaction;

    @Column(name = "tag", nullable = false, length = 100)
    private String tag;

    @Column(name = "public", nullable = false)
    private boolean isPublic;

    @Column(name = "travel_period", nullable = false)
    private Date travelPeriod;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
