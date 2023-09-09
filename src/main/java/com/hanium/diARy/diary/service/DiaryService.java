package com.hanium.diARy.diary.service;

import com.hanium.diARy.diary.dto.*;
import com.hanium.diARy.diary.entity.*;
import com.hanium.diARy.diary.repository.*;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DiaryService {
    private static final Logger logger = LoggerFactory.getLogger(DiaryService.class);

    private final DiaryRepository diaryRepository;
    private final DiaryLocationInterface diaryLocationInterface;
    private final DiaryLocationImageRepository diaryLocationImageRepository;
    private final DiaryLikeRepository diaryLikeRepository;
    private final UserRepositoryInterface userRepositoryInterface;

    public DiaryService(
            @Autowired DiaryRepository diaryRepository,
            @Autowired DiaryLocationInterface diaryLocationInterface,
            @Autowired DiaryLocationImageRepository diaryLocationImageRepository,
            @Autowired DiaryLikeRepository diaryLikeRepository,
            @Autowired UserRepositoryInterface userRepositoryInterface
            ) {
        this.diaryRepository = diaryRepository;
        this.diaryLocationInterface = diaryLocationInterface;
        this.diaryLocationImageRepository = diaryLocationImageRepository;
        this.diaryLikeRepository = diaryLikeRepository;
        this.userRepositoryInterface = userRepositoryInterface;
    }

    public Long createDiary(DiaryRequestDto diaryDto) throws URISyntaxException, IOException {
        //return this.diaryRepository.createDiary(diaryDto, userRepositoryInterface.findByEmail(useremail));
        return this.diaryRepository.createDiary(diaryDto);
    }

    public DiaryResponseDto readDiary(Long id) {
        return diaryRepository.readDiary(id);
    }
    public List<DiaryResponseDto> readPublicDiaryAll() {
        return this.diaryRepository.readPublicDiaryAll();
    }

    public void updateDiary(Long id, DiaryRequestDto diaryDto) {
        this.diaryRepository.updateDiary(id, diaryDto);
    }

    public void deleteDiary(Long id) {
        this.diaryRepository.deleteDiary(id);
    }

    public List<DiaryResponseDto> readDiaryAll() {
        List<DiaryResponseDto> list = new ArrayList<>();

            Iterator<Diary> diaryIterator = this.diaryRepository.readDiaryAll();
            while (diaryIterator.hasNext()) {
                DiaryResponseDto diaryResponseDto = new DiaryResponseDto();
                  Diary diaryEntity = diaryIterator.next();
                  diaryResponseDto = this.readDiary(diaryEntity.getDiaryId());
                  list.add(diaryResponseDto);
            }

        return list;
    }
}
