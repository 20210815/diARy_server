package com.hanium.diARy.home.hottopic.service;

import com.hanium.diARy.diary.dto.DiaryResponseDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.repository.DiaryRepository;
import com.hanium.diARy.diary.repository.DiaryRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final DiaryRepository diaryRepository;

    public TopicService(
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired DiaryRepository diaryRepository
    )
    {
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.diaryRepository = diaryRepository;
    }

    public List<DiaryResponseDto> readAllDiaryOrderByLike() {
        List<Diary> diaries = this.diaryRepositoryInterface.findByIsPublicTrueOrderByLikesCountDesc();
        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();
        for(Diary diary : diaries) {
            DiaryResponseDto diaryResponseDto = diaryRepository.readDiary(diary.getDiaryId());
            diaryResponseDtos.add(diaryResponseDto);
        }
        return diaryResponseDtos;
    }
}
