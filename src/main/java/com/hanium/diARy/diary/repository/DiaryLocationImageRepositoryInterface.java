package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.entity.DiaryLocation;
import com.hanium.diARy.diary.entity.DiaryLocationImage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiaryLocationImageRepositoryInterface extends CrudRepository<DiaryLocationImage, Long> {
    public List<DiaryLocationImage> findByDiaryLocation(DiaryLocation diaryLocation);
}
