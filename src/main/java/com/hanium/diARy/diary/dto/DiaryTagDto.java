package com.hanium.diARy.diary.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanium.diARy.diary.entity.DiaryTag;
import lombok.Data;

@Data
public class DiaryTagDto {
    private Long tagId;
    private String name;

    public DiaryTagDto(String name) {
        this.name = name;
    }

    public DiaryTagDto() {

    }
}
