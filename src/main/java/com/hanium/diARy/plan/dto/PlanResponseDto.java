package com.hanium.diARy.plan.dto;

import com.hanium.diARy.plan.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanResponseDto {
    private PlanDto plan;
    private List<PlanLocationDto> locations;
    private List<PlanTagDto> tags;

    public PlanResponseDto(Plan plan) {
        this.plan = new PlanDto();
        BeanUtils.copyProperties(plan, this.plan);
    }
}
