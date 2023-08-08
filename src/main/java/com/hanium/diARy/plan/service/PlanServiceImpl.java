package com.hanium.diARy.plan.service;

import com.hanium.diARy.plan.dto.*;
import com.hanium.diARy.plan.entity.PlanLocation;
import com.hanium.diARy.plan.entity.Plan;
import com.hanium.diARy.plan.entity.PlanLike;
import com.hanium.diARy.plan.entity.PlanTag;
import com.hanium.diARy.plan.repository.PlanLocationRepository;
import com.hanium.diARy.plan.repository.PlanLikeRepository;
import com.hanium.diARy.plan.repository.PlanRepository;
import com.hanium.diARy.plan.repository.PlanTagRepository;
import com.hanium.diARy.user.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final PlanLocationRepository planLocationRepository;
    private final PlanTagRepository planTagRepository;
    private final PlanLikeRepository planLikeRepository;

    public PlanServiceImpl(PlanRepository planRepository, PlanLocationRepository planLocationRepository, PlanTagRepository planTagRepository, PlanLikeRepository planLikeRepository) {
        this.planRepository = planRepository;
        this.planLocationRepository = planLocationRepository;
        this.planTagRepository = planTagRepository;
        this.planLikeRepository = planLikeRepository;
    }

    @Override
    public Long createPlan(PlanRequestDto request) {
        PlanDto planDto = request.getPlan();
        List<PlanLocationDto> planLocationDtos = request.getLocations();
        List<PlanTagDto> planTagDtos = request.getTags();

        // PlanDto를 이용하여 Plan 엔티티를 생성하고 저장합니다.
        Plan plan = new Plan();

        // 임시 user
        User user = new User();
        user.setUserId(1L);
        plan.setUser(user);

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
                // Handle parsing exception if needed
            }

            location.setPlan(savedPlan);
            savedLocations.add(location);
            planLocationRepository.save(location);
        }

        // TagDto를 이용하여 Tag 엔티티를 생성하고 Plan과 연관시킨 뒤 저장합니다.
        List<PlanTag> savedPlanTags = new ArrayList<>();
        for (PlanTagDto planTagDto : planTagDtos) {
            PlanTag planTag = new PlanTag();
            BeanUtils.copyProperties(planTagDto, planTag);
            planTag.setPlan(savedPlan);
            savedPlanTags.add(planTag);
            planTagRepository.save(planTag);
        }

        // 생성된 Plan 엔티티를 다시 DTO로 변환하여 반환합니다.
    //    PlanDto createdPlanDto = new PlanDto();
    //    BeanUtils.copyProperties(savedPlan, createdPlanDto);

        // PlanDto, List<LocationDto>, List<TagDto>를 PlanResponseDto로 변환하여 반환
    //    PlanResponseDto createdPlanResponseDto = new PlanResponseDto(createdPlanDto, locationDtos, tagDtos);
    //    return createdPlanResponseDto;
        return savedPlan.getPlanId();
    }

    @Override
    public PlanResponseDto updatePlan(Long planId, PlanRequestDto request) {
        System.out.println("" + planId + "," + request);
        // PlanId로 기존 Plan 엔티티를 조회합니다.
        Plan existingPlan = planRepository.findById(planId).orElse(null);
        System.out.println("" + existingPlan);
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
//            if (planDto.getTravelStart() != null) {
//                existingPlan.setTravelStart(planDto.getTravelStart());
//            }
//            if (planDto.getTravelEnd() != null) {
//                existingPlan.setTravelEnd(planDto.getTravelEnd());
//            }
            if (planDto.isPublic() != existingPlan.isPublic()) {
                existingPlan.setPublic(planDto.isPublic());
            }
            // 그 외 필요한 필드들을 업데이트합니다.
            // ...
        }

        // 기존의 PlanLocation 엔티티와 Tag 엔티티들은 그대로 두고 필요한 경우에만 업데이트합니다.
        List<PlanLocationDto> planLocationDtos = request.getLocations();
        if (planLocationDtos != null) {
            for (PlanLocationDto planLocationDto : planLocationDtos) {
                if (planLocationDto.getLocationId() != null) {
                    // locationDto를 사용하여 기존 PlanLocation 엔티티를 찾습니다.
                    PlanLocation existingLocation = planLocationRepository.findById(planLocationDto.getLocationId()).orElse(null);
                    if (existingLocation != null) {
                        // locationDto의 필드들로 기존 PlanLocation 엔티티를 업데이트합니다.
                        if (planLocationDto.getName() != null) {
                            existingLocation.setName(planLocationDto.getName());
                        }
                        if (planLocationDto.getAddress() != null) {
                            existingLocation.setAddress(planLocationDto.getAddress());
                        }
                        if (planLocationDto.getDate() != null) {
                            existingLocation.setDate(planLocationDto.getDate());
                        }
                        if (planLocationDto.getTimeStart() != null) {
                            existingLocation.setTimeStart(planLocationDto.getTimeStart());
                        }
                        if (planLocationDto.getTimeEnd() != null) {
                            existingLocation.setTimeEnd(planLocationDto.getTimeEnd());
                        }
                        // locationRepository.save(existingLocation);
                    }
                }
            }
        }

        List<PlanTagDto> planTagDtos = request.getTags();
        if (planTagDtos != null) {
            for (PlanTagDto planTagDto : planTagDtos) {
                if (planTagDto.getTagId() != null) {
                    // tagDto를 사용하여 기존 Tag 엔티티를 찾습니다.
                    PlanTag existingPlanTag = planTagRepository.findById(planTagDto.getTagId()).orElse(null);
                    if (existingPlanTag != null) {
                        if (planTagDto.getName() != null) {
                            existingPlanTag.setName(planTagDto.getName());
                        }
                        // tagRepository.save(existingTag);
                    }
                }
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
        for (PlanTag planTag : existingPlan.getPlanTags()) {
            PlanTagDto planTagDto = new PlanTagDto();
            BeanUtils.copyProperties(planTag, planTagDto);
            updatedPlanTagDtos.add(planTagDto);
        }

        // Return the updated PlanResponseDto
        PlanResponseDto updatedPlanResponseDto = new PlanResponseDto(updatedPlanDto, updatedPlanLocationDtos, updatedPlanTagDtos);
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
        List<PlanTag> planTags = plan.getPlanTags();
        for (PlanTag planTag : planTags) {
            planTagRepository.delete(planTag);
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
        for (PlanTag planTag : plan.getPlanTags()) {
            PlanTagDto planTagDto = new PlanTagDto();
            BeanUtils.copyProperties(planTag, planTagDto);
            planTagDtos.add(planTagDto);
        }

        // PlanDto, List<LocationDto>, List<TagDto>를 PlanResponseDto로 변환하여 반환
        PlanResponseDto planResponseDto = new PlanResponseDto(planDto, planLocationDtos, planTagDtos);
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
        for (PlanTag planTag : updatedPlan.getPlanTags()) {
            PlanTagDto planTagDto = new PlanTagDto();
            BeanUtils.copyProperties(planTag, planTagDto);
            planTagDtos.add(planTagDto);
        }

        // PlanDto, List<LocationDto>, List<TagDto>를 PlanResponseDto로 변환하여 반환
        PlanResponseDto updatedPlanDto = new PlanResponseDto(planDto, planLocationDtos, planTagDtos);
        return updatedPlanDto;
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
            for (PlanTag planTag : plan.getPlanTags()) {
                PlanTagDto planTagDto = new PlanTagDto();
                BeanUtils.copyProperties(planTag, planTagDto);
                planTagDtos.add(planTagDto);
            }

            PlanResponseDto planResponseDto = new PlanResponseDto(planDto, planLocationDtos, planTagDtos);
            planResponseDtos.add(planResponseDto);
        }

        return planResponseDtos;
    }

}
