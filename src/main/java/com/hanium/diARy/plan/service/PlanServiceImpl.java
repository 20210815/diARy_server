package com.hanium.diARy.plan.service;

import com.hanium.diARy.plan.dto.*;
import com.hanium.diARy.plan.entity.*;
import com.hanium.diARy.plan.repository.*;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final PlanLocationRepository planLocationRepository;
    private final PlanTagRepository planTagRepository;
    private final PlanLikeRepository planLikeRepository;
    private final UserRepositoryInterface userRepositoryInterface;
    private final PlanTagMapRepository planTagMapRepository;

    public PlanServiceImpl(PlanRepository planRepository, PlanLocationRepository planLocationRepository, PlanTagRepository planTagRepository, PlanLikeRepository planLikeRepository, UserRepositoryInterface userRepositoryInterface, PlanTagMapRepository planTagMapRepository) {
        this.planRepository = planRepository;
        this.planLocationRepository = planLocationRepository;
        this.planTagRepository = planTagRepository;
        this.planLikeRepository = planLikeRepository;
        this.userRepositoryInterface = userRepositoryInterface;
        this.planTagMapRepository = planTagMapRepository;
    }

    @Override
    public Long createPlan(PlanRequestDto request) {
        PlanDto planDto = request.getPlan();
        List<PlanLocationDto> planLocationDtos = request.getLocations();
        List<PlanTagDto> planTagDtos = request.getTags();

        // PlanDto를 이용하여 Plan 엔티티를 생성하고 저장합니다.
        Plan plan = new Plan();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepositoryInterface.findByEmail(email);
        plan.setUser(user);
        plan.setOrigin(user);

        BeanUtils.copyProperties(planDto, plan);
        Plan savedPlan = planRepository.save(plan);

        // LocationDto를 이용하여 PlanLocation 엔티티를 생성하고 Plan과 연관시킨 뒤 저장합니다.
        List<PlanLocation> savedLocations = new ArrayList<>();
        for (PlanLocationDto planLocationDto : planLocationDtos) {
            PlanLocation location = new PlanLocation();
            BeanUtils.copyProperties(planLocationDto, location);
            try {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                java.util.Date parsedStartTime = timeFormat.parse(String.valueOf(planLocationDto.getTimeStart()));
                java.util.Date parsedEndTime = timeFormat.parse(String.valueOf(planLocationDto.getTimeEnd()));

                location.setTimeStart(new Time(parsedStartTime.getTime()));
                location.setTimeEnd(new Time(parsedEndTime.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            location.setX(planLocationDto.getX());
            location.setY(planLocationDto.getY());
            location.setPlan(savedPlan);
            savedLocations.add(location);
            planLocationRepository.save(location);
        }

        List<PlanTagMap> savedPlanTags = new ArrayList<>();
        for (PlanTagDto planTagDto : planTagDtos) {
            // PlanTag 엔티티 생성 또는 가져오기
            PlanTag planTag = planTagRepository.findByName(planTagDto.getName());
            if (planTag == null) {
                planTag = new PlanTag();
                planTag.setName(planTagDto.getName());
                planTag = planTagRepository.save(planTag);
            }

            PlanTagMap planTagMap = new PlanTagMap();
            planTagMap.setPlan(savedPlan);
            planTagMap.setPlanTag(planTag);
            savedPlanTags.add(planTagMap);
            planTagMapRepository.save(planTagMap);
        }

        return savedPlan.getPlanId();
    }

    @Transactional
    @Override
    public PlanResponseDto updatePlan(Long planId, PlanRequestDto request) {
        // PlanId로 기존 Plan 엔티티를 조회합니다.
        Plan existingPlan = planRepository.findById(planId).orElse(null);
        if (existingPlan == null) {
            // 예외 처리 등 필요한 로직을 추가할 수 있습니다.
            return null;
        }

        // 수정하고자 하는 필드들만 업데이트합니다.
        PlanDto planDto = request.getPlan();
        if (planDto != null) {
            if (planDto.getTravelDest() != null) {
                existingPlan.setTravelDest(planDto.getTravelDest());
            }
            if (planDto.getContent() != null) {
                existingPlan.setContent(planDto.getContent());
            }
            if (planDto.getTravelStart() != null) {
                existingPlan.setTravelStart(planDto.getTravelStart());
            }
            if (planDto.getTravelEnd() != null) {
                existingPlan.setTravelEnd(planDto.getTravelEnd());
            }
            if (planDto.isPublic() != existingPlan.isPublic()) {
                existingPlan.setPublic(planDto.isPublic());
            }
        }

        List<PlanLocationDto> planLocationDtos = request.getLocations();
        if (planLocationDtos != null) {
            existingPlan.getPlanLocations().clear();
            List<PlanLocation> savedLocations = new ArrayList<>();
            for (PlanLocationDto planLocationDto : planLocationDtos) {
                PlanLocation location = new PlanLocation();
                BeanUtils.copyProperties(planLocationDto, location);
                try {
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    java.util.Date parsedStartTime = timeFormat.parse(String.valueOf(planLocationDto.getTimeStart()));
                    java.util.Date parsedEndTime = timeFormat.parse(String.valueOf(planLocationDto.getTimeEnd()));

                    location.setTimeStart(new Time(parsedStartTime.getTime()));
                    location.setTimeEnd(new Time(parsedEndTime.getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                location.setX(planLocationDto.getX());
                location.setY(planLocationDto.getY());
                location.setPlan(existingPlan);
                savedLocations.add(location);
                planLocationRepository.save(location);
            }
        }

        List<PlanTagDto> planTagDtos = request.getTags();
        if(planTagDtos != null){
            existingPlan.getPlanTagMaps().clear();
            List<PlanTagMap> savedPlanTags = new ArrayList<>();
            for (PlanTagDto planTagDto : planTagDtos) {
                PlanTag planTag = planTagRepository.findByName(planTagDto.getName());
                if (planTag == null) {
                    planTag = new PlanTag();
                    planTag.setName(planTagDto.getName());
                    planTag = planTagRepository.save(planTag);
                }

                PlanTagMap planTagMap = new PlanTagMap();
                planTagMap.setPlan(existingPlan);
                planTagMap.setPlanTag(planTag);
                savedPlanTags.add(planTagMap);
                planTagMapRepository.save(planTagMap);
            }
        }

        // Plan 엔티티를 저장합니다.
        existingPlan = planRepository.save(existingPlan);

        // Update PlanDto, LocationDto, TagDto with the updated Plan entity
        PlanDto updatedPlanDto = new PlanDto();
        BeanUtils.copyProperties(existingPlan, updatedPlanDto);

        List<PlanLocationDto> updatedPlanLocationDtos = new ArrayList<>();
        for (PlanLocation location : existingPlan.getPlanLocations()) {
            PlanLocationDto planLocationDto = new PlanLocationDto();
            BeanUtils.copyProperties(location, planLocationDto);
            updatedPlanLocationDtos.add(planLocationDto);
        }

        List<PlanTagDto> updatedPlanTagDtos = new ArrayList<>();
        for (PlanTagMap planTagMap : existingPlan.getPlanTagMaps()) {
            PlanTagDto planTagDto = new PlanTagDto();
            BeanUtils.copyProperties(planTagMap.getPlanTag(), planTagDto);
            updatedPlanTagDtos.add(planTagDto);
        }

        List<PlanLike> planLikes = planLikeRepository.getAllByPlan_PlanId(planId);
        List<PlanLikeDto> planLikeDtos = new ArrayList<>();
        for (PlanLike planLike : planLikes) {
            PlanLikeDto planLikeDto = new PlanLikeDto();
            planLikeDto.setPlanId(planLike.getPlan().getPlanId());
            planLikeDto.setUserId(planLike.getUser().getUserId());
            planLikeDtos.add(planLikeDto);
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(existingPlan.getUser(), userDto);

        UserDto originDto = new UserDto();
        BeanUtils.copyProperties(existingPlan.getUser(), originDto);

        // Return the updated PlanResponseDto
        PlanResponseDto updatedPlanResponseDto = new PlanResponseDto(userDto, originDto, updatedPlanDto, updatedPlanLocationDtos, updatedPlanTagDtos, planLikeDtos);
        return updatedPlanResponseDto;
    }


    @Override
    public void deletePlan(Long planId) {
        Plan plan = planRepository.findById(planId).orElse(null);
        if (plan == null) {
            // 예외 처리 등 필요한 로직을 추가할 수 있습니다.
            return;
        }

        // Plan 엔티티와 연관된 PlanLocation 엔티티를 모두 삭제합니다.
        List<PlanLocation> locations = plan.getPlanLocations();
        for (PlanLocation location : locations) {
            planLocationRepository.delete(location);
        }

        // Plan 엔티티와 연관된 PlanLike 엔티티를 삭제합니다.
        List<PlanLike> planLikes = plan.getPlanLikes();
        for (PlanLike planLike : planLikes) {
            planLikeRepository.delete(planLike);
        }

        // Plan 엔티티와 연관된 Tag 엔티티를 모두 삭제합니다.
//        List<PlanTag> planTags = plan.getPlanTags();
        List<PlanTagMap> planTagMaps = plan.getPlanTagMaps();
        for (PlanTagMap planTag : planTagMaps) {
            planTagMapRepository.delete(planTag);
        }

        // Plan 엔티티를 삭제합니다.
        planRepository.delete(plan);
    }

    @Override
    public PlanResponseDto getPlanById(Long planId) {
        // PlanId로 Plan 엔티티를 조회하여 PlanDto로 변환해서 반환합니다.
        Plan plan = planRepository.findById(planId).orElse(null);
        if (plan == null) {
            // 예외 처리 등 필요한 로직을 추가할 수 있습니다.
            return null;
        }

        PlanDto planDto = new PlanDto();
        BeanUtils.copyProperties(plan, planDto);

        // Location과 Tag 엔티티들을 DTO로 변환하여 리스트에 담습니다.
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
        BeanUtils.copyProperties(plan.getUser(), userDto);

        UserDto originDto = new UserDto();
        BeanUtils.copyProperties(plan.getOrigin(), originDto);

        // PlanDto, List<LocationDto>, List<TagDto>를 PlanResponseDto로 변환하여 반환
        PlanResponseDto planResponseDto = new PlanResponseDto(userDto, originDto, planDto, planLocationDtos, planTagDtos, planLikeDtos);
        return planResponseDto;
    }

    @Override
    public PlanResponseDto updatePlanIsPublic(Long planId, boolean isPublic) {
        Plan existingPlan = planRepository.findById(planId).orElse(null);
        if (existingPlan == null) {
            return null;
        }

        existingPlan.setPublic(isPublic);
        Plan updatedPlan = planRepository.save(existingPlan);

        PlanDto planDto = new PlanDto();
        BeanUtils.copyProperties(updatedPlan, planDto);

        List<PlanLocationDto> planLocationDtos = new ArrayList<>();
        for (PlanLocation location : updatedPlan.getPlanLocations()) {
            PlanLocationDto planLocationDto = new PlanLocationDto();
            BeanUtils.copyProperties(location, planLocationDto);
            planLocationDtos.add(planLocationDto);
        }

        List<PlanTagDto> planTagDtos = new ArrayList<>();
        for (PlanTagMap planTagMap : updatedPlan.getPlanTagMaps()) {
            PlanTagDto planTagDto = new PlanTagDto();
            BeanUtils.copyProperties(planTagMap, planTagDto);
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
        BeanUtils.copyProperties(existingPlan.getUser(), userDto);

        UserDto originDto = new UserDto();
        BeanUtils.copyProperties(existingPlan.getOrigin(), originDto);

        // PlanDto, List<LocationDto>, List<TagDto>를 PlanResponseDto로 변환하여 반환
        PlanResponseDto planResponseDto = new PlanResponseDto(userDto, originDto, planDto, planLocationDtos, planTagDtos, planLikeDtos);
        return planResponseDto;

    }

    @Override
    public List<PlanResponseDto> getAllPlanByUserId(Long userId) {
        // 사용자 ID에 해당하는 모든 계획을 조회합니다.
        List<Plan> plans = planRepository.findByUser_UserId(userId);

        // 조회된 계획들을 PlanResponseDto 리스트로 변환합니다.
        List<PlanResponseDto> planResponseDtos = new ArrayList<>();
        for (Plan plan : plans) {
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

            Long planId = plan.getPlanId();

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
        }

        return planResponseDtos;
    }

}
