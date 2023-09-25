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

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Transactional
    public void createPlanTakeIn(Long planId, User user) {
        Plan existingPlan = planRepository.findById(planId).orElse(null);
        if (existingPlan == null) {
            throw new IllegalArgumentException("Plan with the given planId does not exist.");
        }

//        User user = new User();
//        user.setUserId(userId);


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

//        List<PlanLocationDto> planLocationDtos = new ArrayList<>();
//        for (PlanLocation location : existingPlan.getPlanLocations()) {
//            PlanLocationDto planLocationDto = new PlanLocationDto();
//            BeanUtils.copyProperties(location, planLocationDto);
//            planLocationDtos.add(planLocationDto);
//        }
//        System.out.println(planLocationDtos);
//
//        // 기존 PlanLocation 정보를 복사합니다.
//        List<PlanLocation> planLocations = new ArrayList<>();
//        for (PlanLocation location : existingPlan.getPlanLocations()) {
//            PlanLocation newLocation = new PlanLocation();
//            BeanUtils.copyProperties(location, newLocation);
//            newLocation.setPlan(newPlan);
//            System.out.println(newLocation);
//            planLocations.add(newLocation);
//        }
//
//        // 새로운 Plan 엔티티를 저장
//        Plan savedPlan = planRepository.save(newPlan);
//        planTakeIn.setPlan(savedPlan);


//
//        // 새로운 PlanLocation 정보를 저장합니다.
//        for (PlanLocation newLocation : savedLocations) {
//            planLocationRepository.save(newLocation);
//        }

//        List<PlanLocation> planLocations = new ArrayList<>();
//        for (PlanLocationDto planLocationDto : planLocationDtos) { // 적절한 PlanLocationDto 목록을 가져와서 사용해야 합니다.
//            PlanLocation location = new PlanLocation();
//            BeanUtils.copyProperties(planLocationDto, location);
//            location.setPlan(savedPlan);
//            planLocations.add(location);
//        }
//        List<PlanLocation> savedLocations = new ArrayList<>();
//        for (PlanLocationDto planLocationDto : planLocationDtos) {
//            PlanLocation location = new PlanLocation();
//            BeanUtils.copyProperties(planLocationDto, location);
//            try {
//                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
//                java.util.Date parsedStartTime = timeFormat.parse(String.valueOf(planLocationDto.getTimeStart()));
//                java.util.Date parsedEndTime = timeFormat.parse(String.valueOf(planLocationDto.getTimeEnd()));
//
//                location.setTimeStart(new Time(parsedStartTime.getTime()));
//                location.setTimeEnd(new Time(parsedEndTime.getTime()));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            location.setX(planLocationDto.getX());
//            location.setY(planLocationDto.getY());
//            location.setPlan(savedPlan);
//            savedLocations.add(location);
//            planLocationRepository.save(location);
//        }

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

//        planTakeInRepository.save(planTakeIn);
//        planLocationRepository.saveAll(planLocations);
    }

    @Override
    @Transactional
    public void deletePlanTakeIn(Long planId, Long userId) {
        planTakeInRepository.deleteByPlan_PlanIdAndUser_UserId(planId, userId);
    }
}
