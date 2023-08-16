package com.hanium.diARy.user.service;

import com.hanium.diARy.diary.CommentMapper;
import com.hanium.diARy.diary.dto.CommentDto;
import com.hanium.diARy.diary.dto.CommentReplyDto;
import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.entity.Comment;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryLike;
import com.hanium.diARy.diary.repository.*;
import com.hanium.diARy.diary.service.DiaryService;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepository;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRepositoryInterface userRepositoryInterface;
    private final DiaryLikeRepository diaryLikeRepository;
    //private final DiaryRepositoryInterface diaryRepositoryInterface;
    //private final CommentRepository commentRepository;
    //private final CommentMapper commentMapper;

    public UserService(
            @Autowired UserRepository userRepository,
            @Autowired DiaryLikeRepository diaryLikeRepository,
            //@Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            //@Autowired CommentRepository commentRepository,
            //@Autowired CommentMapper commentMapper,
            @Autowired UserRepositoryInterface userRepositoryInterface

            ) {
        this.userRepository = userRepository;
        this.diaryLikeRepository = diaryLikeRepository;
        //this.diaryRepositoryInterface = diaryRepositoryInterface;
        //this.commentRepository = commentRepository;
        //this.commentMapper = commentMapper;
        this.userRepositoryInterface = userRepositoryInterface;
    }

    //좋아요 누른 다이어리 확인
    //댓글 확인

    public List<DiaryDto> readAllLikeDiary(Long userId) {
        return this.diaryLikeRepository.findDiaryLikesByUserId(userId);
    }

/*    public List<CommentReplyDto> readAllUserComment(Long userId) {

    }*/





}
