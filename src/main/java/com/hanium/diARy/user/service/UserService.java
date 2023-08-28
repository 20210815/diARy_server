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
            @Autowired DiaryLocationInterface diaryLocationInterface

            ) {
        this.userRepository = userRepository;
        this.diaryLikeRepository = diaryLikeRepository;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.commentRepository = commentRepository;
        //this.commentMapper = commentMapper;
        this.userRepositoryInterface = userRepositoryInterface;
        this.diaryLocationInterface = diaryLocationInterface;
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

            List<DiaryLocationDto> diaryLocationDtoList = new ArrayList<>();
            List<DiaryLocation> diaryLocations = this.diaryLocationInterface.findByDiary_DiaryId(diary.getDiaryId());
            for(DiaryLocation diaryLocation: diaryLocations) {
                DiaryLocationDto diaryLocationDto = new DiaryLocationDto();
                diaryLocationDto.setDiaryId(diary.getDiaryId());
                BeanUtils.copyProperties(diaryLocation, diaryLocationDto);
                diaryLocationDtoList.add(diaryLocationDto);
            }

            List<DiaryTagDto> tagDtos = new ArrayList<>();
            for (DiaryTag tag : diary.getTags()) {
                DiaryTagDto tagDto = new DiaryTagDto();
                BeanUtils.copyProperties(tag, tagDto);
                tagDtos.add(tagDto);
            }
            diaryDto.setTags(tagDtos);

            UserDto userDto = new UserDto();
            User user = userRepositoryInterface.findById(userId).get();
            BeanUtils.copyProperties(user, userDto);
            diaryDto.setLikes(diaryLikeRepository.readDiaryLike(diary.getDiaryId()));
            List<CommentDto> commentDtoList = new ArrayList<>();
            for (Comment comment : commentRepository.readDiaryCommentAll(diary.getDiaryId())) {
                CommentDto commentDto = new CommentDto();
                BeanUtils.copyProperties(comment, commentDto);
                commentDtoList.add(commentDto);
            }
            diaryDto.setComments(commentDtoList);
            DiaryResponseDto diaryResponseDto = new DiaryResponseDto();
            diaryResponseDto.setDiaryDto(diaryDto);
            diaryResponseDto.setUserDto(userDto);
            diaryResponseDto.setDiaryLocationDtoList(diaryLocationDtoList);
            diaryResponseDtos.add(diaryResponseDto);

        }
       return diaryResponseDtos;
    }

}
