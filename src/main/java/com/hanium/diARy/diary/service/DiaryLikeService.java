package com.hanium.diARy.diary.service;

import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.dto.DiaryLikeDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryLike;
import com.hanium.diARy.diary.entity.DiaryLikeId;
import com.hanium.diARy.diary.repository.DiaryLikeRepository;
import com.hanium.diARy.diary.repository.DiaryLikeRepositoryInterface;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DiaryLikeService {
    private final DiaryLikeRepository diaryLikeRepository;
    private final DiaryLikeRepositoryInterface diaryLikeRepositoryInterface;

    public DiaryLikeService(
            @Autowired DiaryLikeRepository diaryLikeRepository,
            @Autowired DiaryLikeRepositoryInterface diaryLikeRepositoryInterface
    ) {
        this.diaryLikeRepository = diaryLikeRepository;
        this.diaryLikeRepositoryInterface = diaryLikeRepositoryInterface;
    }

    public void createDiaryLike(Long diaryId
    ) {
        this.diaryLikeRepository.createDiaryLike(diaryId);
    }

    public DiaryLikeDto readDiaryLike(DiaryLikeId id) {

        return new DiaryLikeDto(

        );
    }

/*    public List<DiaryLikeDto> readDiaryLikeAll() {
        Iterator<DiaryLike> iterator = diaryLikeRepository.readDiaryLikeAll();
        List<DiaryLikeDto> diaryLikeDtoList = new ArrayList<>();

        while (iterator.hasNext()) {
            DiaryLike diaryLike = iterator.next();
            diaryLikeDtoList.add(new DiaryLikeDto(
                    diaryLike.getDiary(),
                    diaryLike.getUser()
            ));
        }

        return diaryLikeDtoList;
    }*/

/*    public void updateDiaryLike(DiaryLikeId id, DiaryLikeDto dto) {
        diaryLikeRepository.updateDiaryLike(id, dto);
    }*/

    public void deleteDiaryLike(Long diaryId) {
        diaryLikeRepository.deleteDiaryLike(diaryId);
    }



/*    public List<DiaryDto> getLikedDiariesByUserId(Long userId) {
        List<Diary> likedDiaries = diaryLikeRepositoryInterface.findByUser_UserId(userId);
        return likedDiaries;
    }*/

    public List<UserDto> getUsersWhoLikedDiaryId(Long diaryId) {
        return this.diaryLikeRepository.findDiaryLikesByDiaryId(diaryId);
    }

/*    public List<DiaryLikeDto> getDiaryLikesByDiaryId(Long diaryId) {
        List<DiaryLike> diaryLikes = diaryLikeRepositoryInterface.findByDiary_DiaryId(diaryId);
        List<DiaryLikeDto> diaryLikeDtos = new ArrayList<>();

        for (DiaryLike diaryLike : diaryLikes) {
            DiaryLikeDto dto = new DiaryLikeDto();
            dto.setDiaryId(diaryLike.getDiary().getId());
            dto.setUserId(diaryLike.getUser().getId());
            // Set other properties as needed
            diaryLikeDtos.add(dto);
        }

        return diaryLikeDtos;
    }*/
}
