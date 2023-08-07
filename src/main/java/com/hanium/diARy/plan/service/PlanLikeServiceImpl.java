package com.hanium.diARy.plan.service;

import com.hanium.diARy.plan.dto.*;
import com.hanium.diARy.plan.entity.Location;
import com.hanium.diARy.plan.entity.Plan;
import com.hanium.diARy.plan.entity.PlanLike;
import com.hanium.diARy.plan.entity.Tag;
import com.hanium.diARy.plan.repository.PlanLikeRepository;
import com.hanium.diARy.plan.repository.PlanRepository;
import com.hanium.diARy.user.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanLikeServiceImpl implements PlanLikeService {

    private final PlanLikeRepository planLikeRepository;
    private final PlanRepository planRepository;

    public PlanLikeServiceImpl(PlanLikeRepository planLikeRepository, PlanRepository planRepository) {
        this.planLikeRepository = planLikeRepository;
        this.planRepository = planRepository;
    }

    @Override
    public List<Long> getAllUserIdsLikesByPlanId(Long planId) {
        return planLikeRepository.getAllUserIdsLikedByPlan_PlanId(planId);
    }

    @Override
    public void createPlanLike(Long planId, PlanLikeDto planLikeDto) {
        PlanLike planLike = new PlanLike();
        // 임시 userId 설정 (요청 바디에서 받아오도록 변경 필요)
        User user = new User();
        user.setUserId(planLikeDto.getUserId());
        planLike.setUser(user);
        BeanUtils.copyProperties(planLikeDto, planLike);

        // PlanId로 Plan 객체를 찾아서 설정합니다.
        Plan plan = planRepository.findById(planId).orElse(null);
        if (plan == null) {
            // 해당 PlanId를 가진 Plan이 없으면 예외 처리
            throw new IllegalArgumentException("Plan with the given planId does not exist.");
        }
        planLike.setPlan(plan);

        planLikeRepository.save(planLike);
    }

    @Override
    @Transactional
    public void deletePlanLike(Long planId, Long userId) {
        planLikeRepository.deleteByPlan_PlanIdAndUser_UserId(planId, userId);
    }

    @Override
    public List<PlanResponseDto> getAllPlanLikedByUserId(Long userId) {
        // 특정 사용자가 좋아요한 모든 PlanLike를 조회합니다.
        List<PlanLike> planLikes = planLikeRepository.findByUser_UserId(userId);

        // 조회된 PlanLike를 토대로 각 Plan을 조회하고 PlanResponseDto 리스트로 변환합니다.
        List<PlanResponseDto> likedPlanResponseDtos = new ArrayList<>();
        for (PlanLike planLike : planLikes) {
            Plan plan = planLike.getPlan();

            PlanDto planDto = new PlanDto();
            BeanUtils.copyProperties(plan, planDto);

            List<LocationDto> locationDtos = new ArrayList<>();
            for (Location location : plan.getLocations()) {
                LocationDto locationDto = new LocationDto();
                BeanUtils.copyProperties(location, locationDto);
                locationDtos.add(locationDto);
            }

            List<TagDto> tagDtos = new ArrayList<>();
            for (Tag tag : plan.getTags()) {
                TagDto tagDto = new TagDto();
                BeanUtils.copyProperties(tag, tagDto);
                tagDtos.add(tagDto);
            }

            PlanResponseDto planResponseDto = new PlanResponseDto(planDto, locationDtos, tagDtos);
            likedPlanResponseDtos.add(planResponseDto);
        }

        return likedPlanResponseDtos;
    }

}
