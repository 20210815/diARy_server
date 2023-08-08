package com.hanium.diARy.user.repository;

import com.hanium.diARy.diary.entity.DiaryLike;
import com.hanium.diARy.diary.repository.DiaryLikeRepository;
import com.hanium.diARy.diary.repository.DiaryLikeRepositoryInterface;
import com.hanium.diARy.diary.repository.DiaryRepositoryInterface;
import com.hanium.diARy.diary.repository.ReplyRepositoryInterface;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    private final UserRepositoryInterface userRepositoryInterface;
    private final ReplyRepositoryInterface replyRepositoryInterface;
    private final DiaryLikeRepositoryInterface diaryLikeRepositoryInterface;

    public UserRepository(
            @Autowired UserRepositoryInterface userRepositoryInterface,
            @Autowired DiaryLikeRepositoryInterface diaryLikeRepositoryInterface,
            @Autowired ReplyRepositoryInterface replyRepositoryInterface
            ){
        this.userRepositoryInterface = userRepositoryInterface;
        this.diaryLikeRepositoryInterface = diaryLikeRepositoryInterface;
        this.replyRepositoryInterface = replyRepositoryInterface;
    }

    public List<DiaryLike> readLikeAllDiary(Long userId) {
        return this.diaryLikeRepositoryInterface.findByUser_UserId(userId);
    }


}
