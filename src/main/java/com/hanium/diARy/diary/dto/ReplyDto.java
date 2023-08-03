package com.hanium.diARy.diary.dto;

import com.hanium.diARy.diary.entity.Comment;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {
    private Comment comment;
    private Diary diary;
    private String content;
    private User user;
}