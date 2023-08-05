package com.hanium.diARy.diary;

import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.entity.Diary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiaryMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public DiaryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // Method to convert Diary to DiaryDto
    public DiaryDto toDto(Diary diary) {
        return modelMapper.map(diary, DiaryDto.class);
    }

    // Method to convert DiaryDto to Diary
    public Diary toEntity(DiaryDto diaryDto) {
        return modelMapper.map(diaryDto, Diary.class);
    }
}