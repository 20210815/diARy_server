package com.hanium.diARy.plan.service;

import com.hanium.diARy.plan.dto.*;
import com.hanium.diARy.plan.entity.Location;
import com.hanium.diARy.plan.entity.Plan;
import com.hanium.diARy.plan.entity.PlanLike;
import com.hanium.diARy.plan.entity.Tag;
import com.hanium.diARy.plan.repository.LocationRepository;
import com.hanium.diARy.plan.repository.PlanLikeRepository;
import com.hanium.diARy.plan.repository.PlanRepository;
import com.hanium.diARy.plan.repository.TagRepository;
import com.hanium.diARy.user.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final LocationRepository locationRepository;
    private final TagRepository tagRepository;
    private final PlanLikeRepository planLikeRepository;

    public PlanServiceImpl(PlanRepository planRepository, LocationRepository locationRepository, TagRepository tagRepository, PlanLikeRepository planLikeRepository) {
        this.planRepository = planRepository;
        this.locationRepository = locationRepository;
        this.tagRepository = tagRepository;
        this.planLikeRepository = planLikeRepository;
    }

    @Override
    public Long createPlan(PlanRequestDto request) {
        PlanDto planDto = request.getPlan();
        List<LocationDto> locationDtos = request.getLocations();
        List<TagDto> tagDtos = request.getTags();

        // PlanDto를 이용하여 Plan 엔티티를 생성하고 저장합니다.
        Plan plan = new Plan();

        // 임시 user
        User user = new User();
        user.setUserId(1L);
        plan.setUser(user);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            plan.setTravelStart(new Date(dateFormat.parse(planDto.getTravelStart()).getTime()));
            plan.setTravelEnd(new Date(dateFormat.parse(planDto.getTravelEnd()).getTime()));
        } catch (ParseException e) {
            // 예외 처리 필요
        }

        BeanUtils.copyProperties(planDto, plan);
        Plan savedPlan = planRepository.save(plan);

        // LocationDto를 이용하여 Location 엔티티를 생성하고 Plan과 연관시킨 뒤 저장합니다.
        List<Location> savedLocations = new ArrayList<>();
        for (LocationDto locationDto : locationDtos) {
            Location location = new Location();
            BeanUtils.copyProperties(locationDto, location);
            location.setPlan(savedPlan);
            savedLocations.add(location);
            locationRepository.save(location);
        }

        // TagDto를 이용하여 Tag 엔티티를 생성하고 Plan과 연관시킨 뒤 저장합니다.
        List<Tag> savedTags = new ArrayList<>();
        for (TagDto tagDto : tagDtos) {
            Tag tag = new Tag();
            BeanUtils.copyProperties(tagDto, tag);
            tag.setPlan(savedPlan);
            savedTags.add(tag);
            tagRepository.save(tag);
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
            if (planDto.getTitle() != null) {
                existingPlan.setTitle(planDto.getTitle());
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

        // 기존의 Location 엔티티와 Tag 엔티티들은 그대로 두고 필요한 경우에만 업데이트합니다.
        List<LocationDto> locationDtos = request.getLocations();
        if (locationDtos != null) {
            for (LocationDto locationDto : locationDtos) {
                if (locationDto.getLocationId() != null) {
                    // locationDto를 사용하여 기존 Location 엔티티를 찾습니다.
                    Location existingLocation = locationRepository.findById(locationDto.getLocationId()).orElse(null);
                    if (existingLocation != null) {
                        // locationDto의 필드들로 기존 Location 엔티티를 업데이트합니다.
                        if (locationDto.getName() != null) {
                            existingLocation.setName(locationDto.getName());
                        }
                        if (locationDto.getAddress() != null) {
                            existingLocation.setAddress(locationDto.getAddress());
                        }
                        if (locationDto.getDate() != null) {
                            existingLocation.setDate(locationDto.getDate());
                        }
                        if (locationDto.getTime() != null) {
                            existingLocation.setTime(locationDto.getTime());
                        }
                        // locationRepository.save(existingLocation);
                    }
                }
            }
        }

        List<TagDto> tagDtos = request.getTags();
        if (tagDtos != null) {
            for (TagDto tagDto : tagDtos) {
                if (tagDto.getTagId() != null) {
                    // tagDto를 사용하여 기존 Tag 엔티티를 찾습니다.
                    Tag existingTag = tagRepository.findById(tagDto.getTagId()).orElse(null);
                    if (existingTag != null) {
                        if (tagDto.getName() != null) {
                            existingTag.setName(tagDto.getName());
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

        List<LocationDto> updatedLocationDtos = new ArrayList<>();
        for (Location location : existingPlan.getLocations()) {
            LocationDto locationDto = new LocationDto();
            BeanUtils.copyProperties(location, locationDto);
            updatedLocationDtos.add(locationDto);
        }

        List<TagDto> updatedTagDtos = new ArrayList<>();
        for (Tag tag : existingPlan.getTags()) {
            TagDto tagDto = new TagDto();
            BeanUtils.copyProperties(tag, tagDto);
            updatedTagDtos.add(tagDto);
        }

        // Return the updated PlanResponseDto
        PlanResponseDto updatedPlanResponseDto = new PlanResponseDto(updatedPlanDto, updatedLocationDtos, updatedTagDtos);
        return updatedPlanResponseDto;
    }


    @Override
    public void deletePlan(Long planId) {
        Plan plan = planRepository.findById(planId).orElse(null);
        if (plan == null) {
            // 예외 처리 등 필요한 로직을 추가할 수 있습니다.
            return;
        }

        // Plan 엔티티와 연관된 Location 엔티티를 모두 삭제합니다.
        List<Location> locations = plan.getLocations();
        for (Location location : locations) {
            locationRepository.delete(location);
        }

        // Plan 엔티티와 연관된 PlanLike 엔티티를 삭제합니다.
        List<PlanLike> planLikes = plan.getPlanLikes();
        for (PlanLike planLike : planLikes) {
            planLikeRepository.delete(planLike);
        }

        // Plan 엔티티와 연관된 Tag 엔티티를 모두 삭제합니다.
        List<Tag> tags = plan.getTags();
        for (Tag tag : tags) {
            tagRepository.delete(tag);
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

        // PlanDto, List<LocationDto>, List<TagDto>를 PlanResponseDto로 변환하여 반환
        PlanResponseDto planResponseDto = new PlanResponseDto(planDto, locationDtos, tagDtos);
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

        List<LocationDto> locationDtos = new ArrayList<>();
        for (Location location : updatedPlan.getLocations()) {
            LocationDto locationDto = new LocationDto();
            BeanUtils.copyProperties(location, locationDto);
            locationDtos.add(locationDto);
        }

        List<TagDto> tagDtos = new ArrayList<>();
        for (Tag tag : updatedPlan.getTags()) {
            TagDto tagDto = new TagDto();
            BeanUtils.copyProperties(tag, tagDto);
            tagDtos.add(tagDto);
        }

        // PlanDto, List<LocationDto>, List<TagDto>를 PlanResponseDto로 변환하여 반환
        PlanResponseDto updatedPlanDto = new PlanResponseDto(planDto, locationDtos, tagDtos);
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
            planResponseDtos.add(planResponseDto);
        }

        return planResponseDtos;
    }

}
