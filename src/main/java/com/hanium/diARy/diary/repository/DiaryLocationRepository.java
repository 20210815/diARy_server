package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.DiaryLocationMapper;
import com.hanium.diARy.diary.DiaryMapper;
import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.dto.DiaryLocationDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryLocation;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DiaryLocationRepository {
    private final DiaryLocationRepositoryInterface diaryLocationRepositoryInterface;
    private final DiaryLocationMapper diaryLocationMapper;
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final DiaryRepository diaryRepository;
    private final DiaryMapper diaryMapper;

    public DiaryLocationRepository(
            @Autowired DiaryLocationRepositoryInterface diaryLocationRepositoryInterface,
            @Autowired DiaryLocationMapper diaryLocationMapper,
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired DiaryMapper diaryMapper,
            @Autowired DiaryRepository diaryRepository
    ){
        this.diaryLocationRepositoryInterface = diaryLocationRepositoryInterface;
        this.diaryLocationMapper = diaryLocationMapper;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.diaryMapper = diaryMapper;
        this.diaryRepository = diaryRepository;
    }
}
