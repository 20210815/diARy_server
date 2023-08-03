package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.entity.DiaryLike;
import com.hanium.diARy.diary.entity.DiaryLikeId;
import org.springframework.data.repository.CrudRepository;

public interface DiaryLikeRepositoryInterface extends CrudRepository<DiaryLike, DiaryLikeId> {
}
