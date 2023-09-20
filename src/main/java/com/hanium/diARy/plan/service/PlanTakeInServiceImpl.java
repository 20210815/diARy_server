package com.hanium.diARy.plan.service;

import com.hanium.diARy.plan.dto.PlanLocationDto;
import com.hanium.diARy.plan.dto.PlanTagDto;
import com.hanium.diARy.plan.entity.*;
import com.hanium.diARy.plan.repository.PlanLocationRepository;
import com.hanium.diARy.plan.repository.PlanRepository;
import com.hanium.diARy.plan.repository.PlanTakeInRepository;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanTakeInServiceImpl implements PlanTakeInService{
    private final PlanTakeInRepository planTakeInRepository;
    private final PlanRepository planRepository;
    private final UserRepositoryInterface userRepositoryInterface;
    private final PlanLocationRepository planLocationRepository;

    public PlanTakeInServiceImpl(PlanTakeInRepository planTakeInRepository, PlanRepository planRepository, UserRepositoryInterface userRepositoryInterface, PlanLocationRepository planLocationRepository) {
        this.planTakeInRepository = planTakeInRepository;
        this.planRepository = planRepository;
        this.userRepositoryInterface = userRepositoryInterface;
        this.planLocationRepository = planLocationRepository;
    }

    @Override
    public void createPlanTakeIn(Long planId, Long userId) {
        PlanTakeIn planTakeIn = new PlanTakeIn();

        User user = new User();
        user.setUserId(userId);
        planTakeIn.setUser(user);

        Plan existingPlan = planRepository.findById(planId).orElse(null);
        if (existingPlan == null) {
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
        newPlan.setImageData(existingPlan.getImageData());
        newPlan.setImageUri(existingPlan.getImageUri());
//        newPlan.setPlanTagMaps(existingPlan.getPlanTagMaps());

        // 새로운 Plan 엔티티를 저장
        Plan savedPlan = planRepository.save(newPlan);
        planTakeIn.setPlan(savedPlan);

        List<PlanLocationDto> planLocationDtos = new ArrayList<>();
        for (PlanLocation location : existingPlan.getPlanLocations()) {
            PlanLocationDto planLocationDto = new PlanLocationDto();
            BeanUtils.copyProperties(location, planLocationDto);
            planLocationDtos.add(planLocationDto);
        }

        List<PlanLocation> planLocations = new ArrayList<>();
        for (PlanLocationDto planLocationDto : planLocationDtos) { // 적절한 PlanLocationDto 목록을 가져와서 사용해야 합니다.
            PlanLocation location = new PlanLocation();
            BeanUtils.copyProperties(planLocationDto, location);
            location.setPlan(savedPlan);
            planLocations.add(location);
        }

//        List<PlanTagDto> planTagDtos = new ArrayList<>();
//        for (PlanTagMap planTagMap : existingPlan.getPlanTagMaps()) {
//            PlanTagDto planTagDto = new PlanTagDto();
//            BeanUtils.copyProperties(planTagMap.getPlanTag(), planTagDto);
//            planTagDtos.add(planTagDto);
//        }

//        List<PlanTag> planTags = new ArrayList<>();
//        for (PlanTagDto planTagDto : planTags) {
//            PlanTagMap planTagmap = new PlanTagMap();
//            BeanUtils.copyProperties(planTagDto, planTagmap);
//            planTagmap.setPlan(existingPlan);
//            planTagmap.setPlanTag(pla);
//
//
//        }
        planTakeInRepository.save(planTakeIn);
        planLocationRepository.saveAll(planLocations);
    }

    @Override
    @Transactional
    public void deletePlanTakeIn(Long planId, Long userId) {
        planTakeInRepository.deleteByPlan_PlanIdAndUser_UserId(planId, userId);
    }
}
