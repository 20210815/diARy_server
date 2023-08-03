package com.hanium.diARy.plan.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlanRequestDto {
    private PlanDto plan;
    private List<LocationDto> locations;
    private List<TagDto> tags;
}
