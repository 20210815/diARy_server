package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.DiaryLocationDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryLocation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiaryLocationRepositoryInterface extends CrudRepository<DiaryLocation, Long> {
    List<DiaryLocation> findByDiary(Diary diaryEntity);
}
