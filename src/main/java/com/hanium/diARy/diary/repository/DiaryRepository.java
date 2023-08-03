package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.entity.Diary;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.Optional;

@Repository
public class DiaryRepository{
    private final DiaryRepositoryInterface diaryRepositoryInterface;

    public DiaryRepository(
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface
    ) {
        this.diaryRepositoryInterface = diaryRepositoryInterface;
    }

    @Transactional
    public void createDiary(DiaryDto diaryDto) {
        // Create a new DiaryEntity instance and set its properties from DiaryDto
        Diary diaryEntity = new Diary();
        diaryEntity.setUser(diaryDto.getUser());
        diaryEntity.setContent(diaryDto.getContent());
        diaryEntity.setPublic(diaryDto.isPublic());
        diaryEntity.setTag(diaryDto.getTag());
        diaryEntity.setTitle(diaryDto.getTitle());
        diaryEntity.setSatisfaction(diaryDto.getSatisfaction());
        diaryEntity.setTravelPeriod(diaryDto.getTravelPeriod());

        // Perform validation if needed (e.g., check for required fields in diaryDto)

        // Save the diaryEntity using the repository
        this.diaryRepositoryInterface.save(diaryEntity);
    }

    public Diary readDiary(Long id) {
        Optional<Diary> diaryEntity = this.diaryRepositoryInterface.findById(id);
        if(diaryEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return diaryEntity.get();

    }

    public Iterator<Diary> readDiaryAll() {
        return this.diaryRepositoryInterface.findAll().iterator();
    }

    public void updateDiary(Long id, DiaryDto diaryDto) {
        Optional<Diary> targetEntity = this.diaryRepositoryInterface.findById(id);
        if(targetEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Diary diaryEntity = targetEntity.get();
        diaryEntity.setTitle(diaryDto.getTitle() == null ? diaryEntity.getTitle() : diaryDto.getTitle());
        diaryEntity.setContent(diaryDto.getContent() == null ? diaryEntity.getContent() : diaryDto.getContent());
        diaryEntity.setTag(diaryDto.getTag() == null? diaryEntity.getTag() : diaryDto.getTag());
        diaryEntity.setUser(diaryEntity.getUser() == null? diaryEntity.getUser() : diaryDto.getUser());
        diaryEntity.setPublic(diaryDto.isPublic());
        diaryEntity.setTravelPeriod(diaryDto.getTravelPeriod() == null? diaryEntity.getTravelPeriod() : diaryDto.getTravelPeriod());
        diaryEntity.setSatisfaction(diaryDto.getSatisfaction() == 0? diaryEntity.getSatisfaction() : diaryDto.getSatisfaction());
        this.diaryRepositoryInterface.save(diaryEntity);
    }

    public void deleteDiary(Long id) {
        Optional<Diary> targetEntity = this.diaryRepositoryInterface.findById(id);
        if(targetEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        this.diaryRepositoryInterface.delete(targetEntity.get());
    }


}