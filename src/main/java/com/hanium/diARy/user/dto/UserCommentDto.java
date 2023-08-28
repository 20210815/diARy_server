package com.hanium.diARy.user.dto;

import com.hanium.diARy.diary.dto.CommentDto;
import com.hanium.diARy.diary.dto.ReplyDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCommentDto {
    private Long diaryId;
    private Long commentId;
    private String content;
}
