package com.hanium.diARy.diary.controller;

import com.hanium.diARy.diary.dto.ReplyDto;
import com.hanium.diARy.diary.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/replies")
public class ReplyController {
    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping
    public void createReply(@RequestBody ReplyDto replyDto) {
        replyService.createReply(replyDto);
    }

    @GetMapping("/{id}")
    public ReplyDto readReply(@PathVariable Long id) {
        ReplyDto replyDto = replyService.readReply(id);
        return replyDto;
    }

    @GetMapping
    public List<ReplyDto> readAllReplies() {
        List<ReplyDto> replyDtoList = replyService.readReplyAll();
        return replyDtoList;
    }

    @PutMapping("/{id}")
    public void updateReply(@PathVariable Long id, @RequestBody ReplyDto replyDto) {
        replyService.updateReply(id, replyDto);
    }

    @DeleteMapping("/{id}")
    public void deleteReply(@PathVariable Long id) {
        replyService.deleteReply(id);
    }
}