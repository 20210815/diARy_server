package com.hanium.diARy.home.hottopic.dto;

import com.hanium.diARy.diary.dto.DiaryResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HottopicDto {
    private String Tagname;
    private List<DiaryResponseDto> diaryResponseDtoList;
}
