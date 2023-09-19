package com.hanium.diARy.rank.service;

import com.hanium.diARy.diary.repository.DiaryLocationInterface;
import com.hanium.diARy.diary.repository.DiaryRepository;
import com.hanium.diARy.diary.repository.DiaryRepositoryInterface;
import com.hanium.diARy.diary.repository.TagRepositoryInterface;
import com.hanium.diARy.plan.dto.*;
import com.hanium.diARy.plan.entity.*;
import com.hanium.diARy.plan.repository.PlanLikeRepository;
import com.hanium.diARy.plan.repository.PlanRepository;
import com.hanium.diARy.plan.repository.PlanTagMapRepository;
import com.hanium.diARy.plan.repository.PlanTagRepository;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RankService {
    private final UserRepositoryInterface userRepositoryInterface;
    private final PlanTagRepository planTagRepository;
    private final PlanRepository planRepository;
    private final PlanLikeRepository planLikeRepository;

    public RankService(
            @Autowired UserRepositoryInterface userRepositoryInterface,
            PlanTagRepository planTagRepository, PlanRepository planRepository, PlanTagMapRepository planTagMapRepository, PlanLikeRepository planLikeRepository) {
        this.userRepositoryInterface = userRepositoryInterface;
        this.planTagRepository = planTagRepository;
        this.planRepository = planRepository;
        this.planLikeRepository = planLikeRepository;
    }

    public List<PlanResponseDto> findPlanByLikeRank() {
        List<Plan> plans = planRepository.findByIsPublicTrue();
        List<PlanResponseDto> planResponseDtos = new ArrayList<>();

        // 중복 방지를 위한 Set
        Set<Long> addedPlanIds = new HashSet<>();

        List<Plan> sortedPlans = plans.stream()
                .sorted((plan1, plan2) -> Integer.compare(plan2.getPlanLikes().size(), plan1.getPlanLikes().size()))
                .limit(20) // 상위 20개 Plan만 반환
                .collect(Collectors.toList());

        for (Plan plan : sortedPlans) {
            Long userId = plan.getUser().getUserId();
            Long planId = plan.getPlanId();

            // 이미 추가한 Plan인지 확인
            if (addedPlanIds.contains(planId)) {
                continue; // 중복된 Plan이면 스킵
            }

            PlanDto planDto = new PlanDto();
            BeanUtils.copyProperties(plan, planDto);

            List<PlanLocationDto> planLocationDtos = new ArrayList<>();
            for (PlanLocation location : plan.getPlanLocations()) {
                PlanLocationDto planLocationDto = new PlanLocationDto();
                BeanUtils.copyProperties(location, planLocationDto);
                planLocationDtos.add(planLocationDto);
            }

            List<PlanTagDto> planTagDtos = new ArrayList<>();
            for (PlanTagMap planTagMap : plan.getPlanTagMaps()) {
                PlanTagDto planTagDto = new PlanTagDto();
                BeanUtils.copyProperties(planTagMap.getPlanTag(), planTagDto);
                planTagDtos.add(planTagDto);
            }

            List<PlanLike> planLikes = planLikeRepository.getAllByPlan_PlanId(planId);
            List<PlanLikeDto> planLikeDtos = new ArrayList<>();
            for (PlanLike planLike : planLikes) {
                PlanLikeDto planLikeDto = new PlanLikeDto();
                planLikeDto.setPlanId(planLike.getPlan().getPlanId());
                planLikeDto.setUserId(planLike.getUser().getUserId());
                planLikeDtos.add(planLikeDto);
            }

            // User 정보도 포함한 PlanResponseDto 생성
            UserDto userDto = new UserDto();
            User user = userRepositoryInterface.findById(userId).get();
            BeanUtils.copyProperties(user, userDto);

            UserDto originDto = new UserDto();
            BeanUtils.copyProperties(plan.getOrigin(), originDto);

            PlanResponseDto planResponseDto = new PlanResponseDto(userDto, originDto, planDto, planLocationDtos, planTagDtos, planLikeDtos);
            planResponseDtos.add(planResponseDto);

            // 이미 추가한 Plan으로 표시
            addedPlanIds.add(planId);
        }

        return planResponseDtos;
    }

    public List<PlanResponseDto> findPlanByRecentRank() {
        List<Plan> plans = planRepository.findByIsPublicTrue();
        List<PlanResponseDto> planResponseDtos = new ArrayList<>();

        // 중복 방지를 위한 Set
        Set<Long> addedPlanIds = new HashSet<>();

        List<Plan> sortedPlans = plans.stream()
                .sorted(Comparator.comparing(Plan::getCreatedAt).reversed())
                .limit(20)
                .collect(Collectors.toList());

        for (Plan plan : sortedPlans) {
            Long userId = plan.getUser().getUserId();
            Long planId = plan.getPlanId();

            // 이미 추가한 Plan인지 확인
            if (addedPlanIds.contains(planId)) {
                continue; // 중복된 Plan이면 스킵
            }

            PlanDto planDto = new PlanDto();
            BeanUtils.copyProperties(plan, planDto);

            List<PlanLocationDto> planLocationDtos = new ArrayList<>();
            for (PlanLocation location : plan.getPlanLocations()) {
                PlanLocationDto planLocationDto = new PlanLocationDto();
                BeanUtils.copyProperties(location, planLocationDto);
                planLocationDtos.add(planLocationDto);
            }

            List<PlanTagDto> planTagDtos = new ArrayList<>();
            for (PlanTagMap planTagMap : plan.getPlanTagMaps()) {
                PlanTagDto planTagDto = new PlanTagDto();
                BeanUtils.copyProperties(planTagMap.getPlanTag(), planTagDto);
                planTagDtos.add(planTagDto);
            }

            List<PlanLike> planLikes = planLikeRepository.getAllByPlan_PlanId(planId);
            List<PlanLikeDto> planLikeDtos = new ArrayList<>();
            for (PlanLike planLike : planLikes) {
                PlanLikeDto planLikeDto = new PlanLikeDto();
                planLikeDto.setPlanId(planLike.getPlan().getPlanId());
                planLikeDto.setUserId(planLike.getUser().getUserId());
                planLikeDtos.add(planLikeDto);
            }

            // User 정보도 포함한 PlanResponseDto 생성
            UserDto userDto = new UserDto();
            User user = userRepositoryInterface.findById(userId).get();
            BeanUtils.copyProperties(user, userDto);

            UserDto originDto = new UserDto();
            BeanUtils.copyProperties(plan.getOrigin(), originDto);

            PlanResponseDto planResponseDto = new PlanResponseDto(userDto, originDto, planDto, planLocationDtos, planTagDtos, planLikeDtos);
            planResponseDtos.add(planResponseDto);

            // 이미 추가한 Plan으로 표시
            addedPlanIds.add(planId);
        }

        return planResponseDtos;
    }
}
