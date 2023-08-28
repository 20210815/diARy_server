package com.hanium.diARy.user.repository;

import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.dto.DiaryLocationDto;
import com.hanium.diARy.diary.dto.DiaryResponseDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryLike;
import com.hanium.diARy.diary.entity.DiaryLocation;
import com.hanium.diARy.diary.repository.*;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final UserRepositoryInterface userRepositoryInterface;
    private final ReplyRepositoryInterface replyRepositoryInterface;
    private final DiaryLikeRepositoryInterface diaryLikeRepositoryInterface;
    private final DiaryLikeRepository diaryLikeRepository;
    private final DiaryRepository diaryRepository;
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final DiaryLocationInterface diaryLocationInterface;

    public UserRepository(
            @Autowired UserRepositoryInterface userRepositoryInterface,
            @Autowired DiaryLikeRepositoryInterface diaryLikeRepositoryInterface,
            @Autowired ReplyRepositoryInterface replyRepositoryInterface,
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired DiaryLocationInterface diaryLocationInterface,
            @Autowired DiaryLikeRepository diaryLikeRepository,
            @Autowired DiaryRepository diaryRepository
            ){
        this.userRepositoryInterface = userRepositoryInterface;
        this.diaryLikeRepositoryInterface = diaryLikeRepositoryInterface;
        this.replyRepositoryInterface = replyRepositoryInterface;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.diaryLocationInterface = diaryLocationInterface;
        this.diaryLikeRepository = diaryLikeRepository;
        this.diaryRepository = diaryRepository;
    }

    public List<DiaryLike> readLikeAllDiary(Long userId) {
        return this.diaryLikeRepositoryInterface.findByUser_UserId(userId);
    }

    public DiaryResponseDto readUserDiary(Long userId, Long diaryId) {
        DiaryResponseDto diaryResponseDto = new DiaryResponseDto();
        User user = this.userRepositoryInterface.findById(userId).get();
        List<Diary> diaries = this.diaryRepositoryInterface.findByUser(user);
        System.out.println(user);
            //diaryResponseDto 1개
            for (Diary diary : diaries) { //다이어리 한 개와  List<DiaryLocation> 저장
                List<DiaryLocationDto> diaryLocationDtoList = new ArrayList<>();
                List<DiaryLocation> diaryLocations = this.diaryLocationInterface.findByDiary_DiaryId(diary.getDiaryId());
                //DTO로 변경................................
                for (DiaryLocation diaryLocation : diaryLocations) {
                    DiaryLocationDto diaryLocationDto = new DiaryLocationDto();
                    BeanUtils.copyProperties(diaryLocation, diaryLocationDto);
                    diaryLocationDto.setDiaryId(diaryLocation.getDiary().getDiaryId());
                    diaryLocationDtoList.add(diaryLocationDto);
                }
                DiaryDto diaryDto = new DiaryDto();
                BeanUtils.copyProperties(diary, diaryDto);
                diaryDto.setLikes(diaryLikeRepository.readDiaryLike(diary.getDiaryId()));
                diaryResponseDto.setDiaryLocationDtoList(diaryLocationDtoList);
                diaryResponseDto.setDiaryDto(diaryDto);
                System.out.println(diaryResponseDto);
            }
            return diaryResponseDto;



    }

    public List<DiaryLocation> readDiaryLocation(Long diaryId) {
        return this.diaryLocationInterface.findByDiary_DiaryId(diaryId);
    }


}
