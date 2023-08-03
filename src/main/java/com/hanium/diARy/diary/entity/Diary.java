package com.hanium.diARy.diary.entity;

import com.hanium.diARy.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
import java.time.Instant;
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

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Column(name = "satisfaction", nullable = false)
    private int satisfaction;

    @OneToMany(mappedBy = "diary", orphanRemoval = true)
    @Column(nullable = true)
    private List<DiaryTag> tags;

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
