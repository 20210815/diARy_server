package com.hanium.diARy.plan.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class PlanLocationDto {
    private Long locationId;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private String name;
    private String address;
}