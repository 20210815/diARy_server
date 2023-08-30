package com.hanium.diARy.map.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapAllDiaryDto {
    private String address;
    private List<MapDiaryDto> diaryDtoList = new ArrayList<>();
}
