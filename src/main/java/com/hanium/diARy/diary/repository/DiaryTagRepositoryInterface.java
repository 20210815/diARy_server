package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.dto.DiaryTagDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryTag;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
@ComponentScan
public interface DiaryTagRepositoryInterface extends CrudRepository<DiaryTag, Long> {
    public DiaryTag findByName(String name);
}
