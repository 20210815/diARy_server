package com.hanium.diARy.diary.service;

import com.hanium.diARy.diary.dto.DiaryLikeDto;
import com.hanium.diARy.diary.entity.DiaryLike;
import com.hanium.diARy.diary.entity.DiaryLikeId;
import com.hanium.diARy.diary.repository.DiaryLikeRepository;
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

    public DiaryLikeService(
            @Autowired DiaryLikeRepository diaryLikeRepository
    ) {
        this.diaryLikeRepository = diaryLikeRepository;
    }

    public void createDiaryLike(DiaryLikeDto dto) {
        diaryLikeRepository.createDiaryLike(dto);
    }

    public DiaryLikeDto readDiaryLike(DiaryLikeId id) {
        DiaryLike diaryLike = diaryLikeRepository.readDiaryLike(id);
        return new DiaryLikeDto(
                diaryLike.getDiary(),
                diaryLike.getUser()
        );
    }

    public List<DiaryLikeDto> readDiaryLikeAll() {
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
    }

    public void updateDiaryLike(DiaryLikeId id, DiaryLikeDto dto) {
        diaryLikeRepository.updateDiaryLike(id, dto);
    }

    public void deleteDiaryLike(DiaryLikeId id) {
        diaryLikeRepository.deleteDiaryLike(id);
    }

    public List<DiaryLikeDto> getLikedDiariesByUserId(Long userId) {
        List<DiaryLike> likedDiaries = diaryLikeRepository.findDiaryLikesByUserId(userId);
        List<DiaryLikeDto> likedDiariesDto = new ArrayList<>();

        for (DiaryLike diaryLike : likedDiaries) {
            likedDiariesDto.add(new DiaryLikeDto(
                    diaryLike.getDiary(), diaryLike.getUser()));
        }

        return likedDiariesDto;
    }

    public List<DiaryLikeDto> getUsersWhoLikedDiaryId(Long diaryId) {
        List<DiaryLike> likedUsers = diaryLikeRepository.findDiaryLikesByDiaryId(diaryId);
        List<DiaryLikeDto> likedUsersDto = new ArrayList<>();

        for (DiaryLike diaryLike : likedUsers) {
            likedUsersDto.add(new DiaryLikeDto(
                    diaryLike.getDiary(), diaryLike.getUser()));
        }

        return likedUsersDto;
    }
}