package com.hanium.diARy.user.service;

import com.hanium.diARy.diary.dto.*;
import com.hanium.diARy.diary.entity.*;
import com.hanium.diARy.diary.repository.*;
import com.hanium.diARy.user.dto.UserCommentDto;
import com.hanium.diARy.user.dto.UserCommentReplyDto;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.dto.UserReplyDto;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepository;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRepositoryInterface userRepositoryInterface;
    private final DiaryLikeRepository diaryLikeRepository;
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    //private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final DiaryRepository diaryRepository;
    private final CommentRepositoryInterface commentRepositoryInterface;
    private final DiaryLocationInterface diaryLocationInterface;
    private final ReplyRepository replyRepository;
    private final ReplyRepositoryInterface replyRepositoryInterface;

    public UserService(
            @Autowired UserRepository userRepository,
            @Autowired DiaryLikeRepository diaryLikeRepository,
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired CommentRepositoryInterface commentRepositoryInterface,
            @Autowired UserRepositoryInterface userRepositoryInterface,
            @Autowired DiaryLocationInterface diaryLocationInterface,
            @Autowired DiaryRepository diaryRepository,
            @Autowired ReplyRepository replyRepository,
            @Autowired ReplyRepositoryInterface replyRepositoryInterface

            ) {
        this.userRepository = userRepository;
        this.diaryLikeRepository = diaryLikeRepository;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.commentRepositoryInterface = commentRepositoryInterface;
        this.userRepositoryInterface = userRepositoryInterface;
        this.diaryLocationInterface = diaryLocationInterface;
        this.diaryRepository = diaryRepository;
        this.replyRepository = replyRepository;
        this.replyRepositoryInterface = replyRepositoryInterface;
    }

    //좋아요 누른 다이어리 확인
    //댓글 확인

    public List<DiaryDto> readAllLikeDiary(Long userId) {
        return this.diaryLikeRepository.findDiaryLikesByUserId(userId);
    }

    public List<UserCommentReplyDto> readAllUserComment(Long userId) {
        List<Comment> comments = commentRepositoryInterface.findByUser_UserId(userId);
        List<UserCommentReplyDto> userCommentReplyDtos = new ArrayList<>();
        for(Comment comment : comments) {
            UserCommentReplyDto userCommentReplyDto = new UserCommentReplyDto();
            //comment 디티오 그대로 그냥 가져가고///
            //아니면 따로 그냥 user에 쓸 comment를 만드는 것도 방법이 될 듯;;;;
            UserCommentDto userCommentDto = new UserCommentDto();
            userCommentDto.setCommentId(comment.getCommentId());
            userCommentDto.setContent(comment.getContent());
            userCommentDto.setDiaryId(comment.getDiary().getDiaryId());
            userCommentDto.setCreatedAt(comment.getCreatedAt());
            userCommentDto.setUpdatedAt(comment.getUpdatedAt());
            userCommentReplyDto.setUserCommentDto(userCommentDto);
            //댓글 추가


            List<Reply> replies = this.replyRepositoryInterface.findByUser_UserIdAndComment_CommentId(userId, comment.getCommentId());
            //List<ReplyDto> replyDtos = this.replyRepository.readUserReplyAll(userId, comment.getCommentId());
            List<UserReplyDto> userReplyDtos = new ArrayList<>();
            for(Reply reply: replies) {
                UserReplyDto userReplyDto = new UserReplyDto();
                userReplyDto.setUpdatedAt(reply.getUpdatedAt());
                userReplyDto.setCommentId(reply.getComment().getCommentId());
                userReplyDto.setContent(reply.getContent());
                userReplyDto.setCreatedAt(reply.getCreatedAt());
                userReplyDto.setDiaryId(reply.getDiary().getDiaryId());
                userReplyDtos.add(userReplyDto);
            }
            userCommentReplyDto.setUserReplyDtos(userReplyDtos);
            userCommentReplyDtos.add(userCommentReplyDto);
        }
        return userCommentReplyDtos;
    }

    public List<DiaryResponseDto> readUserDiary(String email) {
        System.out.println("service");
        List<Diary> diaries = diaryRepositoryInterface.findByUser_Email(email);
        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();

        for (Diary diary: diaries) {
            DiaryDto diaryDto = new DiaryDto();
            BeanUtils.copyProperties(diary, diaryDto);
            DiaryResponseDto diaryResponseDto = this.diaryRepository.readDiary(diary.getDiaryId());
            diaryResponseDtos.add(diaryResponseDto);

        }
       return diaryResponseDtos;
    }

    public UserDto readUser(String email) {
        User user = this.userRepositoryInterface.findByEmail(email);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setUserId(user.getUserId());
        return userDto;
    }

}
