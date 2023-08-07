package com.hanium.diARy.diary.controller;

import com.hanium.diARy.diary.dto.ReplyDto;
import com.hanium.diARy.diary.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diary")
public class ReplyController {
    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/{diaryId}/comment/{commentId}/reply")
    public void createReply(@RequestBody ReplyDto replyDto) {
        replyService.createReply(replyDto);
    }

    //답글 - 댓글로 조회
/*
    @GetMapping("/{diaryId}/comment/{commentId}/reply/{replyId}")
    public ReplyDto readReply(@PathVariable("replyId") Long id) {
        ReplyDto replyDto = replyService.readReply(id);
        return replyDto;
    }
*/

    //diary-댓글별 조회

    @GetMapping("/{diaryId}/comment/{commentId}/reply")
    public List<ReplyDto> readCommentAllReply(@PathVariable("commentId") Long commentId) {
        return this.replyService.readCommentReplyAll(commentId);
    }

    // reply 전체 조회
    @GetMapping("/reply/all")
    public List<ReplyDto> readAllReply() {
        return this.replyService.readReplyAll();
    }



    //user 댓글 조회

/*
    @GetMapping
    public List<ReplyDto> readAllReplies() {
        List<ReplyDto> replyDtoList = replyService.readReplyAll();
        return replyDtoList;
    }
*/

    @PutMapping("/{diaryId}/comment/{commentId}/reply/{replyId}")
    public void updateReply(@PathVariable("replyId") Long id, @RequestBody ReplyDto replyDto) {
        replyService.updateReply(id, replyDto);
    }

    @DeleteMapping("/{diaryId}/comment/{commentId}/reply/{replyId}")
    public void deleteReply(@PathVariable("replyId") Long id) {
        replyService.deleteReply(id);
    }
}