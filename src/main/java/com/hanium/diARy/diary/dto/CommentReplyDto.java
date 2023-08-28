package com.hanium.diARy.diary.dto;

import com.hanium.diARy.diary.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentReplyDto {
    private CommentDto commentDto;
    private List<ReplyDto> replyDtoList;
}
