package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.List;

public interface DiaryRepositoryInterface extends CrudRepository<Diary, Long> {
    public Iterable<Diary> findByIsPublicTrue();
}
