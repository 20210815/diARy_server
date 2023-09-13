package com.hanium.diARy.plan.service;

public interface PlanTakeInService {
    void createPlanTakeIn(Long planId, Long userId);
    void deletePlanTakeIn(Long planId, Long userId);
}
