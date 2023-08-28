package com.hanium.diARy.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReplyDto {
    private Long commentId;
    private Long diaryId;
    private String content;
}
