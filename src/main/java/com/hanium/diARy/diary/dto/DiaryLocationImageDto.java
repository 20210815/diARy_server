package com.hanium.diARy.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.net.URI;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryLocationImageDto {
    private Long imageId;
    private Long diaryLocationId;
    private String imageData;
    private String imageUri;

    // 생성자, 게터/세터, 기타 메서드
}
