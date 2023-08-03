package com.hanium.diARy.diary.dto;

import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.Date;

@Data
public class CommentDto {
    private Diary diary;
    private String content;
    private User user;

    public CommentDto() {
    }

    public CommentDto(Diary diary, String content, User user) {
        this.diary = diary;
        this.content = content;
        this.user = user;
    }
}