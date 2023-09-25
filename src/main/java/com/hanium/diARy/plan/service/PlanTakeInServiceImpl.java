package com.hanium.diARy.plan.service;

import com.hanium.diARy.plan.dto.PlanTagDto;
import com.hanium.diARy.plan.entity.*;
import com.hanium.diARy.plan.repository.*;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanTakeInServiceImpl implements PlanTakeInService{
    private final PlanTakeInRepository planTakeInRepository;
    private final PlanRepository planRepository;
    private final UserRepositoryInterface userRepositoryInterface;
    private final PlanLocationRepository planLocationRepository;
    private final PlanTagRepository planTagRepository;
    private final PlanTagMapRepository planTagMapRepository;

    public PlanTakeInServiceImpl(PlanTakeInRepository planTakeInRepository, PlanRepository planRepository, UserRepositoryInterface userRepositoryInterface, PlanLocationRepository planLocationRepository, PlanTagRepository planTagRepository, PlanTagMapRepository planTagMapRepository1) {
        this.planTakeInRepository = planTakeInRepository;
        this.planRepository = planRepository;
        this.userRepositoryInterface = userRepositoryInterface;
        this.planLocationRepository = planLocationRepository;
        this.planTagRepository = planTagRepository;
        this.planTagMapRepository = planTagMapRepository1;
    }

    @Override
    @Transactional
    public void createPlanTakeIn(Long planId, User user) {
        Plan existingPlan = planRepository.findById(planId).orElse(null);
        if (existingPlan == null) {
            throw new IllegalArgumentException("Plan with the given planId does not exist.");
        }

        // 새로운 Plan 엔티티를 생성하고 필요한 정보를 복사
        Plan newPlan = new Plan();
        newPlan.setUser(user); // 현재 사용자를 작성자로 설정
        newPlan.setOrigin(existingPlan.getOrigin()); // 원래 작성자를 원작자로 설정
        newPlan.setTravelDest(existingPlan.getTravelDest());
        newPlan.setContent(existingPlan.getContent());
        newPlan.setTravelStart(existingPlan.getTravelStart());
        newPlan.setTravelEnd(existingPlan.getTravelEnd());
        newPlan.setPublic(existingPlan.isPublic());
        newPlan.setImageData(existingPlan.getImageData());
        newPlan.setImageUri(existingPlan.getImageUri());

        // 새로운 Plan 엔티티 저장
        planRepository.save(newPlan);

        // PlanTakeIn 엔티티 생성
        PlanTakeIn planTakeIn = new PlanTakeIn();
        planTakeIn.setPlan(newPlan);
        planTakeIn.setUser(user);

        // PlanTakeIn 엔티티 저장
        planTakeInRepository.save(planTakeIn);

        List<PlanLocation> existingLocations = existingPlan.getPlanLocations();
        List<PlanLocation> newLocations = new ArrayList<>();

        for (PlanLocation existingLocation : existingLocations) {
            PlanLocation newLocation = new PlanLocation();
            newLocation.setPlan(newPlan);
            newLocation.setName(existingLocation.getName());
            newLocation.setX(existingLocation.getX());
            newLocation.setY(existingLocation.getY());
            newLocation.setAddress(existingLocation.getAddress());
            newLocation.setDate(existingLocation.getDate());
            newLocation.setTimeStart(existingLocation.getTimeStart());
            newLocation.setTimeEnd(existingLocation.getTimeEnd());

            newLocations.add(newLocation);
        }

        // 새로운 PlanLocation 엔티티들 저장
        planLocationRepository.saveAll(newLocations);

        List<PlanTagMap> existingTagMaps = existingPlan.getPlanTagMaps();
        List<PlanTagMap> newTags = new ArrayList<>();
        for (PlanTagMap planTagMap : existingTagMaps) {
            // PlanTag 엔티티 생성 또는 가져오기
            PlanTag planTag = planTagRepository.findByName(planTagMap.getPlanTag().getName());
            if (planTag == null) {
                planTag = new PlanTag();
                planTag.setName(planTagMap.getPlanTag().getName());
                planTag = planTagRepository.save(planTag);
            }

            PlanTagMap newPlanTagMap = new PlanTagMap();
            newPlanTagMap.setPlan(newPlan);
            newPlanTagMap.setPlanTag(planTag);
            newTags.add(newPlanTagMap);
            planTagMapRepository.save(newPlanTagMap);
        }
    }

    @Override
    @Transactional
    public void deletePlanTakeIn(Long planId, Long userId) {
        planTakeInRepository.deleteByPlan_PlanIdAndUser_UserId(planId, userId);
    }
}
