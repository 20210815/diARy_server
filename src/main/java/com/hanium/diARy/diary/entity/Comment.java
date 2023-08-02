package com.hanium.diARy.diary.entity;

import com.hanium.diARy.user.entity.User;
import lombok.Data;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @ManyToOne
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    private String content;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}