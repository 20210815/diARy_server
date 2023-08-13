package com.hanium.diARy.map.dto;


import com.hanium.diARy.diary.dto.DiaryLocationDto;
import com.hanium.diARy.diary.entity.DiaryLocation;
import com.hanium.diARy.user.dto.UserDto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapDiaryDto {
    private int satisfaction;
    private Long diaryId;
    private DiaryLocationDto diaryLocationDto;
    private Date travelStart;
    private Date travelEnd;
    private UserDto userDto;
}
