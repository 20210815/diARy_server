package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.entity.DiaryLike;
import com.hanium.diARy.diary.entity.DiaryLikeId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiaryLikeRepositoryInterface extends CrudRepository<DiaryLike, DiaryLikeId> {
    public List<DiaryLike> findByUser_UserId(Long userId);
    public List<DiaryLike> findByDiary_DiaryId(Long diaryId);
}
