package com.hanium.diARy.diary.service;

import com.hanium.diARy.diary.repository.TagRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    private final TagRepositoryInterface tagRepositoryInterface;

    public TagService(
            @Autowired TagRepositoryInterface tagRepositoryInterface
    ) {
        this.tagRepositoryInterface = tagRepositoryInterface;
    }
}
