package com.hanium.diARy.diary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanium.diARy.user.entity.User;
import lombok.Data;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;

@Data
@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new java.sql.Date(Instant.now().toEpochMilli());
        this.updatedAt = new java.sql.Date(Instant.now().toEpochMilli());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new java.sql.Date(Instant.now().toEpochMilli());
    }
}