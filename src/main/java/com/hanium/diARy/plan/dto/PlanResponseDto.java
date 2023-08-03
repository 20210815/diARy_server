package com.hanium.diARy.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanResponseDto {
    private PlanDto plan;
    private List<LocationDto> locations;
    private List<TagDto> tags;

}
