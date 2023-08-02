package com.hanium.diARy.diary.entity;

import com.hanium.diARy.user.entity.User;
import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private int replyId;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
