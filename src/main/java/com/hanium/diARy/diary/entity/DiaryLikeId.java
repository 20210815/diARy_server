package com.hanium.diARy.diary.entity;

import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DiaryLikeId implements Serializable {
    private Long user;
    private Long diary;
}