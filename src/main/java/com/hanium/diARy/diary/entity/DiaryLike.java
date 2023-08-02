package com.hanium.diARy.diary.entity;

import com.hanium.diARy.user.entity.User;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "diary_like")
@IdClass(DiaryLikeId.class) // 복합 기본 키 클래스를 지정
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