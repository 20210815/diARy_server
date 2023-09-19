package com.hanium.diARy.diary.entity;

import com.hanium.diARy.plan.entity.Plan;
import com.hanium.diARy.plan.entity.PlanLocation;
import com.hanium.diARy.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long diaryId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "travel_dest")
    private String travelDest;

    private String memo;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "satisfaction", nullable = false)
    private int satisfaction;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "diary_diary_tag", // 다른 이름으로 변경
            joinColumns = @JoinColumn(name = "diary_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<DiaryTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DiaryLike> diaryLikes;

    @Column(name="likes_count")
    private int likesCount = 0;

    @Column(name = "public", nullable = false)
    private boolean isPublic;

    @Column(name = "travel_start", nullable = false)
    private Date travelStart;

    @Column(name = "travel_end", nullable = false)
    private Date travelEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryLocation> diaryLocations;


    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Column(name = "comment_id")
    private List<Comment> comments = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
