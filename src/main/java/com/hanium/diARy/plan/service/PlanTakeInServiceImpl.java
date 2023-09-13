package com.hanium.diARy.plan.service;

import com.hanium.diARy.plan.entity.Plan;
import com.hanium.diARy.plan.entity.PlanLike;
import com.hanium.diARy.plan.entity.PlanTakeIn;
import com.hanium.diARy.plan.repository.PlanRepository;
import com.hanium.diARy.plan.repository.PlanTakeInRepository;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PlanTakeInServiceImpl implements PlanTakeInService{
    private final PlanTakeInRepository planTakeInRepository;
    private final PlanRepository planRepository;
    private final UserRepositoryInterface userRepositoryInterface;

    public PlanTakeInServiceImpl(PlanTakeInRepository planTakeInRepository, PlanRepository planRepository, UserRepositoryInterface userRepositoryInterface) {
        this.planTakeInRepository = planTakeInRepository;
        this.planRepository = planRepository;
        this.userRepositoryInterface = userRepositoryInterface;
    }

    @Override
    public void createPlanTakeIn(Long planId, Long userId) {
        PlanTakeIn planTakeIn = new PlanTakeIn();

        User user = new User();
        user.setUserId(userId);
        planTakeIn.setUser(user);

        Plan plan = planRepository.findById(planId).orElse(null);
        if(plan == null) {
            throw new IllegalArgumentException("Plan with the given planId does not exist.");
        }
        planTakeIn.setPlan(plan);

        planTakeInRepository.save(planTakeIn);
    }

    @Override
    @Transactional
    public void deletePlanTakeIn(Long planId, Long userId) {
        planTakeInRepository.deleteByPlan_PlanIdAndUser_UserId(planId, userId);
    }
}
