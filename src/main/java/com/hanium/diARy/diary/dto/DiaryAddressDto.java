package com.hanium.diARy.diary.dto;

import com.hanium.diARy.diary.entity.Diary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryAddressDto {
    private String x;
    private String y;
    private String address;
}
