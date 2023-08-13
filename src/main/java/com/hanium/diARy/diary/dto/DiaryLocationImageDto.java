package com.hanium.diARy.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryLocationImageDto {
    private Long imageId;
    private Long diaryLocationId;
    private byte[] imageData;

    // 생성자, 게터/세터, 기타 메서드
}
