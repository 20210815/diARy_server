package com.hanium.diARy.diary.dto;


import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.user.entity.User;
import lombok.Data;

@Data
public class DiaryLikeDto {
    private Diary diary;
    private User user;

    public DiaryLikeDto() {
    }

    public DiaryLikeDto(Diary diary, User user) {
        this.diary = diary;
        this.user = user;
    }
}