package com.hanium.diARy.plan.service;

import com.hanium.diARy.plan.dto.PlanDto;
import com.hanium.diARy.plan.dto.PlanImageDto;
import com.hanium.diARy.plan.dto.PlanRequestDto;
import com.hanium.diARy.plan.dto.PlanResponseDto;

import java.util.List;

public interface PlanService {
    Long createPlan(PlanRequestDto request);
    PlanResponseDto updatePlan(Long planId, PlanRequestDto request);
    void deletePlan(Long planId);
    PlanResponseDto getPlanById(Long planId);
    PlanResponseDto updatePlanIsPublic(Long planId, boolean isPublic);
    List<PlanResponseDto> getAllPlanByUserId(Long userId);
    void updatePlanImage(Long planId, PlanImageDto planImageDto);
}
