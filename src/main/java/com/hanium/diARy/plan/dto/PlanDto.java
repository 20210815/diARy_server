package com.hanium.diARy.plan.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class PlanDto {
    private Long planId;
    private String title;
    private String content;
    private String travelStart;
    private String travelEnd;
    private Date createdAt;
    private Date updatedAt;
    private boolean isPublic;
}