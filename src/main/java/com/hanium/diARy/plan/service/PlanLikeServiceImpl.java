package com.hanium.diARy.plan.service;

import com.hanium.diARy.plan.dto.*;
import com.hanium.diARy.plan.entity.*;
import com.hanium.diARy.plan.repository.PlanLikeRepository;
import com.hanium.diARy.plan.repository.PlanRepository;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanLikeServiceImpl implements PlanLikeService {

    private final PlanLikeRepository planLikeRepository;
    private final PlanRepository planRepository;
    private final UserRepositoryInterface userRepositoryInterface;

    public PlanLikeServiceImpl(PlanLikeRepository planLikeRepository, PlanRepository planRepository, UserRepositoryInterface userRepositoryInterface) {
        this.planLikeRepository = planLikeRepository;
        this.planRepository = planRepository;
        this.userRepositoryInterface = userRepositoryInterface;
    }

    @Override
    public List<UserDto> getAllUserIdsLikesByPlanId(Long planId) {
        // PlanLikeRepository에서 직접 UserDto를 반환할 수 없으므로, PlanLike 객체들을 조회하고 직접 변환해야 합니다.
        List<PlanLike> planLikes = planLikeRepository.getAllByPlan_PlanId(planId);

        List<UserDto> userDtos = new ArrayList<>();
        for (PlanLike planLike : planLikes) {
            User user = planLike.getUser();
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDtos.add(userDto);
        }

        return userDtos;
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

            List<PlanLocationDto> planLocationDtos = new ArrayList<>();
            for (PlanLocation location : plan.getPlanLocations()) {
                PlanLocationDto planLocationDto = new PlanLocationDto();
                BeanUtils.copyProperties(location, planLocationDto);
                planLocationDtos.add(planLocationDto);
            }

            List<PlanTagDto> planTagDtos = new ArrayList<>();
            for (PlanTagMap planTag : plan.getPlanTagMaps()) {
                PlanTagDto planTagDto = new PlanTagDto();
                BeanUtils.copyProperties(planTag, planTagDto);
                planTagDtos.add(planTagDto);
            }

            // User 정보도 포함한 PlanResponseDto 생성
            UserDto userDto = new UserDto();
            User user = userRepositoryInterface.findById(userId).get();
            BeanUtils.copyProperties(user, userDto);

            PlanResponseDto planResponseDto = new PlanResponseDto(userDto, planDto, planLocationDtos, planTagDtos);
            likedPlanResponseDtos.add(planResponseDto);
        }

        return likedPlanResponseDtos;
    }

    public Long getPlanLikeCount(Long planId) {
        List<PlanLike> planLikes = planLikeRepository.getAllByPlan_PlanId(planId);

        List<UserDto> userDtos = new ArrayList<>();
        for (PlanLike planLike : planLikes) {
            User user = planLike.getUser();
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDtos.add(userDto);
        }
        return Long.valueOf(planLikes.size());
    }
}
