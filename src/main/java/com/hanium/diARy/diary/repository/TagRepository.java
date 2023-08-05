package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.plan.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TagRepository {
    private final TagRepositoryInterface tagRepositoryInterface;

    public TagRepository(
            @Autowired TagRepositoryInterface tagRepositoryInterface
            ){
        this.tagRepositoryInterface = tagRepositoryInterface;
    }
}
