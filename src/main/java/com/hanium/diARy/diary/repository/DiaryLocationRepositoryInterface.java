package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryLocation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiaryLocationRepositoryInterface extends CrudRepository<DiaryLocation, Long> {
    public List<DiaryLocation> findByDiary_DiaryId(Long diaryId);
    public List<DiaryLocation> findByAddress(String address);
}
