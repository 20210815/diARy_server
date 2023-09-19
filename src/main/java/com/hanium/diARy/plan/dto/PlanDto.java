package com.hanium.diARy.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanDto {
    private Long planId;
    private String travelDest;
    private String content;
    private Date travelStart;
    private Date travelEnd;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean isPublic;
    private String imageData;
    private String imageUri;
}