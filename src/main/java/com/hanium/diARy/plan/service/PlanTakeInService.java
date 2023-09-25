package com.hanium.diARy.plan.service;

import com.hanium.diARy.user.entity.User;

public interface PlanTakeInService {
    void createPlanTakeIn(Long planId, User user);
    void deletePlanTakeIn(Long planId, Long userId);
}
