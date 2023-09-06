package com.hanium.diARy.home.search.service;

import com.hanium.diARy.diary.dto.DiaryResponseDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryTag;
import com.hanium.diARy.diary.repository.*;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final TagRepositoryInterface tagRepositoryInterface;
    private final DiaryLocationInterface diaryLocationInterface;
    private final UserRepositoryInterface userRepositoryInterface;
    private final DiaryRepository diaryRepository;
    private final PlanTagRepository planTagRepository;
    private final PlanRepository planRepository;
    private final PlanLikeRepository planLikeRepository;

    public SearchService(
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired TagRepositoryInterface tagRepositoryInterface,
            @Autowired DiaryLocationInterface diaryLocationInterface,
            @Autowired UserRepositoryInterface userRepositoryInterface,
            @Autowired DiaryRepository diaryRepository,
            PlanTagRepository planTagRepository, PlanRepository planRepository, PlanTagMapRepository planTagMapRepository, PlanLikeRepository planLikeRepository) {
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.tagRepositoryInterface = tagRepositoryInterface;
        this.diaryLocationInterface = diaryLocationInterface;
        this.userRepositoryInterface = userRepositoryInterface;
        this.diaryRepository = diaryRepository;
        this.planTagRepository = planTagRepository;
        this.planRepository = planRepository;
        this.planLikeRepository = planLikeRepository;
    }

    public List<DiaryResponseDto> findDiaryByTag(String searchword) {
        System.out.println("service");
        DiaryTag diaryTag = tagRepositoryInterface.findByName(searchword);
        List<Diary> diaries = diaryTag.getDiaries();
        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();

        //좋아요 많은 순서대로 출력
        List<Diary> sortedDiaries = diaries.stream()
                .sorted(Comparator.comparingInt(Diary::getLikesCount).reversed())
                .collect(Collectors.toList());

        for (Diary diary: sortedDiaries) {
            DiaryResponseDto diaryResponseDto = diaryRepository.readDiary(diary.getDiaryId());
            diaryResponseDtos.add(diaryResponseDto);

        }
        return diaryResponseDtos;
    }

    public List<PlanResponseDto> findPlanByTag(String searchword) {
        PlanTag planTag = planTagRepository.findByName(searchword);
        List<Long> planIds = planRepository.findPlanIdsByTagId(planTag.getTagId());
        List<Plan> plans = planRepository.findAllById(planIds);
        List<PlanResponseDto> planResponseDtos = new ArrayList<>();

        for (Plan plan : plans) {
            Long userId = plan.getUser().getUserId();
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

            PlanResponseDto planResponseDto = new PlanResponseDto(userDto, planDto, planLocationDtos, planTagDtos, planLikeDtos);
            planResponseDtos.add(planResponseDto);
        }

        return planResponseDtos;
    }

    public List<DiaryResponseDto> findDiaryByWriter(String searchword) {
        List<Diary> diaries = diaryRepositoryInterface.findByUserUsernameContaining(searchword);
        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();
        List<Diary> sortedDiaries = diaries.stream()
                .sorted(Comparator.comparingInt(Diary::getLikesCount).reversed())
                .collect(Collectors.toList());

        for (Diary diary: sortedDiaries) {
            DiaryResponseDto diaryResponseDto = diaryRepository.readDiary(diary.getDiaryId());
            diaryResponseDtos.add(diaryResponseDto);

        }
        return diaryResponseDtos;
    }

    public List<DiaryResponseDto> findDiaryByDest(String searchword) {
        List<Diary> diaries = diaryRepositoryInterface.findByTravelDestContaining(searchword);
        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();
        List<Diary> sortedDiaries = diaries.stream()
                .sorted(Comparator.comparingInt(Diary::getLikesCount).reversed())
                .collect(Collectors.toList());

        for (Diary diary: sortedDiaries) {
            DiaryResponseDto diaryResponseDto = diaryRepository.readDiary(diary.getDiaryId());
            diaryResponseDtos.add(diaryResponseDto);

        }
        return diaryResponseDtos;
    }
}
