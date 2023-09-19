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

        Plan existingPlan = planRepository.findById(planId).orElse(null);
        if(existingPlan == null) {
            throw new IllegalArgumentException("Plan with the given planId does not exist.");
        }

        // 새로운 Plan 엔티티를 생성하고 필요한 정보를 복사
        Plan newPlan = new Plan();
        newPlan.setUser(user); // 현재 사용자를 작성자로 설정
        newPlan.setOrigin(existingPlan.getUser()); // 원래 작성자를 원작자로 설정
        newPlan.setTravelDest(existingPlan.getTravelDest());
        newPlan.setContent(existingPlan.getContent());
        newPlan.setTravelStart(existingPlan.getTravelStart());
        newPlan.setTravelEnd(existingPlan.getTravelEnd());
        newPlan.setPublic(existingPlan.isPublic());

        // 새로운 Plan 엔티티를 저장
        Plan savedPlan = planRepository.save(newPlan);

        planTakeIn.setPlan(savedPlan); // 새로운 Plan을 설정

        // PlanTakeIn 엔티티를 저장
        planTakeInRepository.save(planTakeIn);
    }

    @Override
    @Transactional
    public void deletePlanTakeIn(Long planId, Long userId) {
        planTakeInRepository.deleteByPlan_PlanIdAndUser_UserId(planId, userId);
    }
}
