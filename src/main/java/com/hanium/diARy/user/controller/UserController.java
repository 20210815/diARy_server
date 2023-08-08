package com.hanium.diARy.user.controller;

import com.hanium.diARy.diary.dto.CommentDto;
import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(
            @Autowired UserService userService
    ){
        this.userService = userService;
    }

    @GetMapping("/{userId}/diary-like")
    public List<DiaryDto> readUserLikeDiary(@PathVariable("userId") Long id) {
        return this.userService.readAllLikeDiary(id);
    }

/*    @GetMapping("/{userId}/diary-comment")
    public List<CommentDto> readUserDiaryComment(@PathVariable("userId") Long id) {
        return this.userService.readAllCommentReply(id);
    }*/
}
