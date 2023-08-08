package com.hanium.diARy.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryRequestDto {
    private DiaryDto diaryDto;
    private List<DiaryLocationDto> diaryLocationDtoList = new ArrayList<>();
};
