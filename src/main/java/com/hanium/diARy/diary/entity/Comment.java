package com.hanium.diARy.diary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanium.diARy.user.entity.User;
import lombok.Data;
import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    @Column(name = "reply_id")
    private List<Reply> replies = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", diaryId=" + (diary != null ? diary.getDiaryId() : null) +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", userId=" + (user != null ? user.getUserId() : null) +
                ", updatedAt=" + updatedAt +
                ", numReplies=" + replies.size() + // 여기서는 댓글의 개수만 표시
                '}';
    }
}