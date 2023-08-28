package com.hanium.diARy.user.service;

import com.hanium.diARy.diary.CommentMapper;
import com.hanium.diARy.diary.dto.*;
import com.hanium.diARy.diary.entity.*;
import com.hanium.diARy.diary.repository.*;
import com.hanium.diARy.diary.service.DiaryService;
import com.hanium.diARy.plan.dto.PlanDto;
import com.hanium.diARy.plan.dto.PlanLocationDto;
import com.hanium.diARy.plan.dto.PlanResponseDto;
import com.hanium.diARy.plan.dto.PlanTagDto;
import com.hanium.diARy.plan.entity.Plan;
import com.hanium.diARy.plan.entity.PlanLocation;
import com.hanium.diARy.plan.entity.PlanTag;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepository;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.beans.BeanUtils;
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
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    //private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final DiaryRepository diaryRepository;
    private final CommentRepository commentRepository;
    //private final CommentMapper commentMapper;
    private final DiaryLocationInterface diaryLocationInterface;

    public UserService(
            @Autowired UserRepository userRepository,
            @Autowired DiaryLikeRepository diaryLikeRepository,
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired CommentRepository commentRepository,
            //@Autowired CommentMapper commentMapper,
            @Autowired UserRepositoryInterface userRepositoryInterface,
            @Autowired DiaryLocationInterface diaryLocationInterface,
            @Autowired DiaryRepository diaryRepository

            ) {
        this.userRepository = userRepository;
        this.diaryLikeRepository = diaryLikeRepository;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.commentRepository = commentRepository;
        this.userRepositoryInterface = userRepositoryInterface;
        this.diaryLocationInterface = diaryLocationInterface;
        this.diaryRepository = diaryRepository;
    }

    //좋아요 누른 다이어리 확인
    //댓글 확인

    public List<DiaryDto> readAllLikeDiary(Long userId) {
        return this.diaryLikeRepository.findDiaryLikesByUserId(userId);
    }

/*    public List<CommentReplyDto> readAllUserComment(Long userId) {

    }*/

    public List<DiaryResponseDto> readUserDiary(Long userId) {
        System.out.println("service");
        List<Diary> diaries = diaryRepositoryInterface.findByUser_UserId(userId);
        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();

        for (Diary diary: diaries) {
            DiaryDto diaryDto = new DiaryDto();
            BeanUtils.copyProperties(diary, diaryDto);
            DiaryResponseDto diaryResponseDto = this.diaryRepository.readDiary(diary.getDiaryId());
            diaryResponseDtos.add(diaryResponseDto);

        }
       return diaryResponseDtos;
    }

}
