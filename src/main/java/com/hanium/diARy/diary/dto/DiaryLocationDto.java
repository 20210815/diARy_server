package com.hanium.diARy.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryLocationDto {
    private Long diaryLocationId;
    private Long diaryId;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private String content;
    private String name;
    private String address;
}
