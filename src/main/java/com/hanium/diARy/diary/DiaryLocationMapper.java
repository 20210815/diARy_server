package com.hanium.diARy.diary;

import com.hanium.diARy.diary.dto.DiaryLocationDto;
import com.hanium.diARy.diary.entity.DiaryLocation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiaryLocationMapper {

    private final ModelMapper modelMapper;

    public DiaryLocationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DiaryLocationDto mapToDto(DiaryLocation diaryLocation) {
        return modelMapper.map(diaryLocation, DiaryLocationDto.class);
    }

    public List<DiaryLocationDto> mapToDtoList(List<DiaryLocation> diaryLocations) {
        return diaryLocations.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public DiaryLocation mapToEntity(DiaryLocationDto diaryLocationDto) {
        return modelMapper.map(diaryLocationDto, DiaryLocation.class);
    }

    public List<DiaryLocation> mapToEntityList(List<DiaryLocationDto> diaryLocationDtos) {
        return diaryLocationDtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}