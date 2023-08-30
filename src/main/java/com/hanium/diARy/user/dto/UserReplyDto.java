package com.hanium.diARy.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReplyDto {
    private Long commentId;
    private Long diaryId;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}
