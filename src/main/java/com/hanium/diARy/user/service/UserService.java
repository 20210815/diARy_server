package com.hanium.diARy.user.service;

import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryLike;
import com.hanium.diARy.diary.repository.DiaryLikeRepository;
import com.hanium.diARy.diary.repository.DiaryLikeRepositoryInterface;
import com.hanium.diARy.diary.repository.DiaryRepository;
import com.hanium.diARy.diary.repository.DiaryRepositoryInterface;
import com.hanium.diARy.diary.service.DiaryService;
import com.hanium.diARy.user.repository.UserRepository;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final DiaryLikeRepository diaryLikeRepository;
    private final DiaryRepositoryInterface diaryRepositoryInterface;

    public UserService(
            @Autowired UserRepository userRepository,
            @Autowired DiaryLikeRepository diaryLikeRepository,
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface
    ) {
        this.userRepository = userRepository;
        this.diaryLikeRepository = diaryLikeRepository;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
    }

    //좋아요 누른 다이어리 확인
    //댓글 확인

    public List<DiaryDto> readAllLikeDiary(Long userId) {
        List<DiaryLike> diaryLikes = this.userRepository.readLikeAllDiary(userId);
        return this.diaryLikeRepository.findDiaryLikesByUserId(userId);
        /*
        List<DiaryDto> diaryDtoList = new ArrayList<>();

        for (DiaryLike diaryLike : diaryLikes) {
            //해당 다이어리를 읽어서 DTo로 변환

            DiaryDto diaryDto = new DiaryDto();
            //diaryDto.setLikes(diary.getDiaryLikes());
            //diaryLike.getDiary();
        }
        return diaryDtoList;*/
    }



}
