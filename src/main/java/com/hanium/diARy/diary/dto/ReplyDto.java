package com.hanium.diARy.diary.dto;

import com.hanium.diARy.diary.entity.Comment;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.user.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class ReplyDto {
    private Comment comment;
    private Diary diary;
    private String content;
    private User user;


    public ReplyDto() {
    }

    public ReplyDto(Comment comment, Diary diary, String content, User user) {
        this.comment = comment;
        this.diary = diary;
        this.content = content;
        this.user = user;
    }
}