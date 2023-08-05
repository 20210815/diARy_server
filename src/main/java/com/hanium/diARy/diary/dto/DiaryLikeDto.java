package com.hanium.diARy.diary.dto;


import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.user.entity.User;
import lombok.Data;

@Data
public class DiaryLikeDto {
    private Long diaryId;
    private Long userId;
}