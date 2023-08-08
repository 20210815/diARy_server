package com.hanium.diARy.diary.entity;

import com.hanium.diARy.plan.entity.Plan;
import com.hanium.diARy.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
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

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "travel_dest")
    private String travelDest;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "diary_location_id")
    private List<DiaryLocation> diaryLocations = new ArrayList<>();

    @Column(name = "satisfaction", nullable = false)
    private int satisfaction;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "diary_diary_tag", // 다른 이름으로 변경
            joinColumns = @JoinColumn(name = "diary_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<DiaryTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryLike> diaryLikes;

    @Column(name = "public", nullable = false)
    private boolean isPublic;

    @Column(name = "travel_start", nullable = false)
    private Date travelStart;

    @Column(name = "travel_end", nullable = false)
    private Date travelEnd;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "comment_id")
    private List<Comment> comments = new ArrayList<>();

    private String memo;



    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date(Instant.now().toEpochMilli());
        this.updatedAt = new Date(Instant.now().toEpochMilli());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date(Instant.now().toEpochMilli());
    }

}
