package com.hanium.diARy.plan.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class PlanDto {
    private Long planId;
    private String travelDest;
    private String content;
    private Date travelStart;
    private Date travelEnd;
    private Date createdAt;
    private Date updatedAt;
    private boolean isPublic;
}