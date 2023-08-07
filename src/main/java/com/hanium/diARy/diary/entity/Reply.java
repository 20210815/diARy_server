package com.hanium.diARy.diary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanium.diARy.user.entity.User;
import lombok.Data;
import jakarta.persistence.*;

import java.sql.Date;
import java.time.Instant;

@Data
@Entity
@Table(name = "Reply")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;


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

