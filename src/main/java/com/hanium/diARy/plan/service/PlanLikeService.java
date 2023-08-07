package com.hanium.diARy.plan.service;

import com.hanium.diARy.plan.dto.PlanLikeDto;
import com.hanium.diARy.plan.dto.PlanResponseDto;

import java.util.List;

public interface PlanLikeService {
    List<Long> getAllUserIdsLikesByPlanId(Long planId);
    void createPlanLike(Long planId, PlanLikeDto planLikeDto);
    void deletePlanLike(Long planId, Long userId);
    List<PlanResponseDto> getAllPlanLikedByUserId(Long userId);
}
