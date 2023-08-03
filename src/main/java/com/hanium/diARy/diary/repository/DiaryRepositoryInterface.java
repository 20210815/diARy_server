package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.entity.Diary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiaryRepositoryInterface extends CrudRepository<Diary, Long> {
}
