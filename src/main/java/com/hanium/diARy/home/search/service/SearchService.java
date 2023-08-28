package com.hanium.diARy.home.search.service;

import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.dto.DiaryLocationDto;
import com.hanium.diARy.diary.dto.DiaryResponseDto;
import com.hanium.diARy.diary.dto.DiaryTagDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryLocation;
import com.hanium.diARy.diary.entity.DiaryTag;
import com.hanium.diARy.diary.repository.DiaryLocationInterface;
import com.hanium.diARy.diary.repository.DiaryRepository;
import com.hanium.diARy.diary.repository.DiaryRepositoryInterface;
import com.hanium.diARy.diary.repository.DiaryTagRepositoryInterface;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final DiaryTagRepositoryInterface diaryTagRepositoryInterface;
    private final DiaryLocationInterface diaryLocationInterface;
    private final UserRepositoryInterface userRepositoryInterface;
    private final DiaryRepository diaryRepository;

    public SearchService(
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired DiaryTagRepositoryInterface diaryTagRepositoryInterface,
            @Autowired DiaryLocationInterface diaryLocationInterface,
            @Autowired UserRepositoryInterface userRepositoryInterface,
            @Autowired DiaryRepository diaryRepository
            ) {
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.diaryTagRepositoryInterface = diaryTagRepositoryInterface;
        this.diaryLocationInterface = diaryLocationInterface;
        this.userRepositoryInterface = userRepositoryInterface;
        this.diaryRepository = diaryRepository;
    }

    public List<DiaryResponseDto> findDiaryByTag(String searchword) {
        System.out.println("service");
        DiaryTag diaryTag = diaryTagRepositoryInterface.findByName(searchword);
        List<Diary> diaries = diaryTag.getDiaries();
        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();

        for (Diary diary: diaries) {
            DiaryResponseDto diaryResponseDto = diaryRepository.readDiary(diary.getDiaryId());
            diaryResponseDtos.add(diaryResponseDto);

        }
        return diaryResponseDtos;
    }
}
