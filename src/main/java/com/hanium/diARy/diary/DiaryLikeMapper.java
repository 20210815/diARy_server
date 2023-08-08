package com.hanium.diARy.diary;

import com.hanium.diARy.diary.dto.DiaryLikeDto;
import com.hanium.diARy.diary.entity.DiaryLike;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiaryLikeMapper {

    private final ModelMapper modelMapper;

    public DiaryLikeMapper(
            @Autowired ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // DiaryLike 엔티티를 DiaryLikeDto로 변환하는 함수
    public DiaryLikeDto toDto(DiaryLike diaryLike) {
        return modelMapper.map(diaryLike, DiaryLikeDto.class);
    }

    // DiaryLikeDto를 DiaryLike 엔티티로 변환하는 함수 (필요시)
    public DiaryLike toEntity(DiaryLikeDto diaryLikeDto) {
        return modelMapper.map(diaryLikeDto, DiaryLike.class);
    }

    public List<DiaryLikeDto> toDtoList(List<DiaryLike> diaryLikes) {
        return diaryLikes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // List<DiaryLikeDto> DTO 리스트를 List<DiaryLike> 엔티티 리스트로 변환하는 함수
    public List<DiaryLike> toEntityList(List<DiaryLikeDto> diaryLikeDtos) {
        return diaryLikeDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
