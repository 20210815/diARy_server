package com.hanium.diARy.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCommentReplyDto {
    private UserCommentDto userCommentDto;
    private List<UserReplyDto> userReplyDtos;
}
