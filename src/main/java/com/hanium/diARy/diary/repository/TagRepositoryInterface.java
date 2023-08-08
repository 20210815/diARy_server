package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.DiaryTagDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepositoryInterface extends JpaRepository<DiaryTag, Long> {
    public DiaryTag findByName(String name);
}
