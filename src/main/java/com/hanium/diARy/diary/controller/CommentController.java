package com.hanium.diARy.diary.controller;

import com.hanium.diARy.diary.dto.CommentDto;
import com.hanium.diARy.diary.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CommentDto commentDto) {
        commentService.createComment(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comment created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> readComment(@PathVariable Long id) {
        CommentDto commentDto = commentService.readComment(id);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> readCommentAll() {
        List<CommentDto> commentDtoList = commentService.readCommentAll();
        return ResponseEntity.ok(commentDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody CommentDto dto) {
        commentService.updateComment(id, dto);
        return ResponseEntity.ok("Comment updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
