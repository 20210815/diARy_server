package com.hanium.diARy.plan.service;

import com.hanium.diARy.plan.dto.PlanLikeDto;
import com.hanium.diARy.plan.dto.PlanResponseDto;
import com.hanium.diARy.user.dto.UserDto;

import java.util.List;

public interface PlanLikeService {
    List<UserDto> getAllUserIdsLikesByPlanId(Long planId);
    void createPlanLike(Long planId, Long userId);
    void deletePlanLike(Long planId, Long userId);
    List<PlanResponseDto> getAllPlanLikedByUserId(Long userId);
    Long getPlanLikeCount(Long planId);
}
