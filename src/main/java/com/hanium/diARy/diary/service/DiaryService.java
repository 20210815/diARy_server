package com.hanium.diARy.diary.service;

import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.repository.DiaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DiaryService {
    private static final Logger logger = LoggerFactory.getLogger(DiaryService.class);

    private final DiaryRepository diaryRepository;

    public DiaryService(
            @Autowired DiaryRepository diaryRepository
    ) {
        this.diaryRepository = diaryRepository;
    }

    public void createDiary(DiaryDto diaryDto) {
        this.diaryRepository.createDiary(diaryDto);
    }

    public DiaryDto readDiary(Long id) {
        Diary diaryEntity = this.diaryRepository.readDiary(id);
        return new DiaryDto(
                diaryEntity.getTitle(),
                diaryEntity.getContent(),
                diaryEntity.getTag(),
                diaryEntity.getSatisfaction(),
                diaryEntity.getTravelPeriod(),
                diaryEntity.getUser()
        );
    }

    public List<DiaryDto> readDiaryAll() {
        Iterator<Diary> iterator = this.diaryRepository.readDiaryAll();
        List<DiaryDto> diaryDtoList = new ArrayList<>();
        while(iterator.hasNext()) {
            Diary diaryEntity = iterator.next();
            diaryDtoList.add(new DiaryDto(
                    diaryEntity.getTitle(),
                    diaryEntity.getContent(),
                    diaryEntity.getTag(),
                    diaryEntity.getSatisfaction(),
                    diaryEntity.getTravelPeriod(),
                    diaryEntity.getUser()
            ));
        }
        return diaryDtoList;
    }

    public void updateDiary(Long id, DiaryDto diaryDto) {
        this.diaryRepository.updateDiary(id, diaryDto);
    }

    public void deleteDiary(Long id) {
        this.diaryRepository.deleteDiary(id);
    }
}
