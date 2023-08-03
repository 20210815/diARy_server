package com.hanium.diARy.diary.controller;

import com.hanium.diARy.diary.dto.CommentDto;
import com.hanium.diARy.diary.dto.ReplyDto;
import com.hanium.diARy.diary.service.CommentService;
import com.hanium.diARy.diary.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diary")
public class CommentController {
    private final CommentService commentService;
    private final ReplyService replyService;

    public CommentController(@Autowired CommentService commentService,
                             @Autowired ReplyService replyService) {
        this.commentService = commentService;
        this.replyService = replyService;
    }

    @PostMapping("/{diaryId}/comment")
    public void createComment(@RequestBody CommentDto commentDto) {
        commentService.createComment(commentDto);
    }

/*
    @GetMapping("/{diaryId}/comment/{commentId}")
    public CommentDto readComment(@PathVariable("commentId") Long commentId,
                                  @PathVariable("diaryId") Long diaryId) {
        CommentDto commentDto = commentService.readComment(commentId);
        return commentDto;
    }
*/

    @GetMapping("/{diaryId}/comment")
    public List<CommentDto> readDiaryCommentAll(@PathVariable("diaryId") Long id) {
        List<CommentDto> commentDtoList = commentService.readDiaryCommentAll(id);
        return commentDtoList;
    }

    //유저별 댓글 답글 조회
    @GetMapping("/{userId}/comment")
    public List<CommentDto> readUserCommentAll(@PathVariable("userId") Long id) {
        List<CommentDto> commentDtoList = commentService.readUserCommentAll(id);
        return commentDtoList;
    }

    @PutMapping("/{diaryId}/comment/{commentId}")
    public void updateComment(@PathVariable("commentId") Long id, @RequestBody CommentDto dto) {
        commentService.updateComment(id, dto);
    }

    @DeleteMapping("/{diaryId}/comment/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long id) {
        commentService.deleteComment(id);
    }
}
