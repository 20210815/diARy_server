package com.hanium.diARy.diary.entity;

import com.hanium.diARy.user.entity.User;
import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
@IdClass(DiaryLikeId.class)
public class DiaryLike {
    @Id
    @ManyToOne
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}