package com.hanium.diARy.user.controller;

import com.hanium.diARy.diary.dto.CommentDto;
import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.dto.DiaryResponseDto;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import com.hanium.diARy.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepositoryInterface userRepositoryInterface;

    public UserController(
            @Autowired UserService userService,
            @Autowired UserRepositoryInterface userRepositoryInterface
    ){
        this.userService = userService;
        this.userRepositoryInterface = userRepositoryInterface;
    }

    @GetMapping("/{userId}/diary-like")
    public List<DiaryDto> readUserLikeDiary(@PathVariable("userId") Long id) {
        return this.userService.readAllLikeDiary(id);
    }

/*    @GetMapping("/{userId}/diary-comment")
    public List<CommentDto> readUserDiaryComment(@PathVariable("userId") Long id) {
        return this.userService.readAllCommentReply(id);
    }*/


    @GetMapping("/{userId}/diary")
    public List<DiaryResponseDto> readUserDiary(@PathVariable("userId") Long id) {
        return this.userService.readUserDiary(id);
    }

    @GetMapping("/diary")
    public List<DiaryResponseDto> readUserDiary() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepositoryInterface.findByEmail(email);
        return this.userService.readUserDiary(user.getUserId());
    }
}
