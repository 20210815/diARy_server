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

import java.util.*;
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



    public List<PlanResponseDto> findPlanByTagLike(String searchword) {
        PlanTag planTag = planTagRepository.findByNameContaining(searchword);
        List<Long> planIds = planRepository.findPlanIdsByTagId(planTag.getTagId());
        List<Plan> plans = planRepository.findAllById(planIds);
        List<PlanResponseDto> planResponseDtos = new ArrayList<>();

        // 중복 방지를 위한 Set
        Set<Long> addedPlanIds = new HashSet<>();

        List<Plan> sortedPlans = plans.stream()
                .sorted((plan1, plan2) -> Integer.compare(plan2.getPlanLikes().size(), plan1.getPlanLikes().size()))
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

            PlanResponseDto planResponseDto = new PlanResponseDto(userDto, planDto, planLocationDtos, planTagDtos, planLikeDtos);
            planResponseDtos.add(planResponseDto);

            // 이미 추가한 Plan으로 표시
            addedPlanIds.add(planId);
        }

        return planResponseDtos;
    }


    public List<PlanResponseDto> findPlanByWriterLike(String searchword) {
        List<Plan> plans = planRepository.findByUserUsernameContainingAndIsPublicTrue(searchword);
        List<PlanResponseDto> planResponseDtos = new ArrayList<>();

        // 중복 방지를 위한 Set
        Set<Long> addedPlanIds = new HashSet<>();

        List<Plan> sortedPlans = plans.stream()
                .sorted((plan1, plan2) -> Integer.compare(plan2.getPlanLikes().size(), plan1.getPlanLikes().size()))
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

            PlanResponseDto planResponseDto = new PlanResponseDto(userDto, planDto, planLocationDtos, planTagDtos, planLikeDtos);
            planResponseDtos.add(planResponseDto);

            // 이미 추가한 Plan으로 표시
            addedPlanIds.add(planId);
        }

        return planResponseDtos;
    }

    public List<PlanResponseDto> findPlanByDestLike(String searchword) {
        List<Plan> plans = planRepository.findByTravelDestContainingAndIsPublicTrue(searchword);
        List<PlanResponseDto> planResponseDtos = new ArrayList<>();

        // 중복 방지를 위한 Set
        Set<Long> addedPlanIds = new HashSet<>();

        List<Plan> sortedPlans = plans.stream()
                .sorted((plan1, plan2) -> Integer.compare(plan2.getPlanLikes().size(), plan1.getPlanLikes().size()))
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

            PlanResponseDto planResponseDto = new PlanResponseDto(userDto, planDto, planLocationDtos, planTagDtos, planLikeDtos);
            planResponseDtos.add(planResponseDto);

            // 이미 추가한 Plan으로 표시
            addedPlanIds.add(planId);
        }

        return planResponseDtos;
    }

    public List<PlanResponseDto> findPlanByTagRecent(String searchword) {
        PlanTag planTag = planTagRepository.findByNameContaining(searchword);
        List<Long> planIds = planRepository.findPlanIdsByTagId(planTag.getTagId());
        List<Plan> plans = planRepository.findAllById(planIds);
        List<PlanResponseDto> planResponseDtos = new ArrayList<>();

        // 중복 방지를 위한 Set
        Set<Long> addedPlanIds = new HashSet<>();

        List<Plan> sortedPlans = plans.stream()
                .sorted(Comparator.comparing(Plan::getCreatedAt).reversed())
                .collect(Collectors.toList());

        for (Plan plan : sortedPlans) {
            Long userId = plan.getUser().getUserId();
            Long planId = plan.getPlanId();

            // 이미 추가한 Plan인지 확인
            if (addedPlanIds.contains(planId) || (plan.isPublic()) == false) {
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

            PlanResponseDto planResponseDto = new PlanResponseDto(userDto, planDto, planLocationDtos, planTagDtos, planLikeDtos);
            planResponseDtos.add(planResponseDto);

            // 이미 추가한 Plan으로 표시
            addedPlanIds.add(planId);
        }

        return planResponseDtos;
    }

    public List<PlanResponseDto> findPlanByWriterRecent(String searchword) {
        List<Plan> plans = planRepository.findByUserUsernameContainingAndIsPublicTrue(searchword);
        List<PlanResponseDto> planResponseDtos = new ArrayList<>();

        // 중복 방지를 위한 Set
        Set<Long> addedPlanIds = new HashSet<>();

        List<Plan> sortedPlans = plans.stream()
                .sorted(Comparator.comparing(Plan::getCreatedAt).reversed())
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

            PlanResponseDto planResponseDto = new PlanResponseDto(userDto, planDto, planLocationDtos, planTagDtos, planLikeDtos);
            planResponseDtos.add(planResponseDto);

            // 이미 추가한 Plan으로 표시
            addedPlanIds.add(planId);
        }

        return planResponseDtos;
    }

    public List<PlanResponseDto> findPlanByDestRecent(String searchword) {
        List<Plan> plans = planRepository.findByTravelDestContainingAndIsPublicTrue(searchword);
        List<PlanResponseDto> planResponseDtos = new ArrayList<>();

        // 중복 방지를 위한 Set
        Set<Long> addedPlanIds = new HashSet<>();

        List<Plan> sortedPlans = plans.stream()
                .sorted(Comparator.comparing(Plan::getCreatedAt).reversed())
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

            PlanResponseDto planResponseDto = new PlanResponseDto(userDto, planDto, planLocationDtos, planTagDtos, planLikeDtos);
            planResponseDtos.add(planResponseDto);

            // 이미 추가한 Plan으로 표시
            addedPlanIds.add(planId);
        }

        return planResponseDtos;
    }
    public List<DiaryResponseDto> findDiaryByTagLike(String searchword) {
        System.out.println("service");
        List<DiaryTag> diaryTags = tagRepositoryInterface.findByNameContaining(searchword);
        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();

        Set<Long> uniqueDiaryIds = new HashSet<>(); // 중복된 Diary ID를 제거하기 위한 Set

        for (DiaryTag diaryTag : diaryTags) {
            List<Diary> diaries = diaryTag.getDiaries();
            List<Diary> sortedDiaries = diaries.stream()
                    .sorted(Comparator.comparingInt(Diary::getLikesCount).reversed())
                    .collect(Collectors.toList());

            for (Diary diary : sortedDiaries) {
                if (diary.isPublic() && uniqueDiaryIds.add(diary.getDiaryId())) {
                    DiaryResponseDto diaryResponseDto = diaryRepository.readDiary(diary.getDiaryId());
                    diaryResponseDtos.add(diaryResponseDto);
                }
            }
        }

        return diaryResponseDtos;
    }


    public List<DiaryResponseDto> findDiaryByTagRecent(String searchword) {
        System.out.println("service");
        List<DiaryTag> diaryTags = tagRepositoryInterface.findByNameContaining(searchword);
        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();

        Set<Long> uniqueDiaryIds = new HashSet<>(); // 중복된 Diary ID를 제거하기 위한 Set

        for (DiaryTag diaryTag : diaryTags) {
            List<Diary> diaries = diaryTag.getDiaries();
            List<Diary> sortedDiaries = diaries.stream()
                    .sorted(Comparator.comparing(Diary::getCreatedAt).reversed())
                    .collect(Collectors.toList());

            for (Diary diary : sortedDiaries) {
                if (diary.isPublic() && uniqueDiaryIds.add(diary.getDiaryId())) {
                    DiaryResponseDto diaryResponseDto = diaryRepository.readDiary(diary.getDiaryId());
                    diaryResponseDtos.add(diaryResponseDto);
                }
            }
        }

        return diaryResponseDtos;
    }

    public List<DiaryResponseDto> findDiaryByWriterLike(String searchword) {
        List<Diary> diaries = diaryRepositoryInterface.findByUserUsernameContaining(searchword);
        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();

        // 중복 방지를 위한 Set
        Set<Long> addedDiaryIds = new HashSet<>();

        List<Diary> sortedDiaries = diaries.stream()
                .sorted(Comparator.comparingInt(Diary::getLikesCount).reversed())
                .collect(Collectors.toList());

        for (Diary diary : sortedDiaries) {
            // 이미 추가한 Diary인지 확인
            if (addedDiaryIds.contains(diary.getDiaryId())) {
                continue; // 중복된 Diary면 스킵
            }

            if (diary.isPublic()) {
                DiaryResponseDto diaryResponseDto = diaryRepository.readDiary(diary.getDiaryId());
                diaryResponseDtos.add(diaryResponseDto);
            }

            // 이미 추가한 Diary로 표시
            addedDiaryIds.add(diary.getDiaryId());
        }

        return diaryResponseDtos;
    }


    public List<DiaryResponseDto> findDiaryByDestLike(String searchword) {
        List<Diary> diaries = diaryRepositoryInterface.findByTravelDestContaining(searchword);
        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();

        // 중복 방지를 위한 Set
        Set<Long> addedDiaryIds = new HashSet<>();

        List<Diary> sortedDiaries = diaries.stream()
                .sorted(Comparator.comparingInt(Diary::getLikesCount).reversed())
                .collect(Collectors.toList());

        for (Diary diary : sortedDiaries) {
            // 이미 추가한 Diary인지 확인
            if (addedDiaryIds.contains(diary.getDiaryId())) {
                continue; // 중복된 Diary면 스킵
            }

            if (diary.isPublic()) {
                DiaryResponseDto diaryResponseDto = diaryRepository.readDiary(diary.getDiaryId());
                diaryResponseDtos.add(diaryResponseDto);
            }

            // 이미 추가한 Diary로 표시
            addedDiaryIds.add(diary.getDiaryId());
        }

        return diaryResponseDtos;
    }



    public List<DiaryResponseDto> findDiaryByWriterRecent(String searchword) {
        List<Diary> diaries = diaryRepositoryInterface.findByUserUsernameContaining(searchword);
        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();

        // 중복 방지를 위한 Set
        Set<Long> addedDiaryIds = new HashSet<>();

        List<Diary> sortedDiaries = diaries.stream()
                .sorted(Comparator.comparing(Diary::getCreatedAt).reversed())
                .collect(Collectors.toList());

        for (Diary diary : sortedDiaries) {
            // 이미 추가한 Diary인지 확인
            if (addedDiaryIds.contains(diary.getDiaryId())) {
                continue; // 중복된 Diary면 스킵
            }

            if (diary.isPublic()) {
                DiaryResponseDto diaryResponseDto = diaryRepository.readDiary(diary.getDiaryId());
                diaryResponseDtos.add(diaryResponseDto);
            }

            // 이미 추가한 Diary로 표시
            addedDiaryIds.add(diary.getDiaryId());
        }

        return diaryResponseDtos;
    }


    public List<DiaryResponseDto> findDiaryByDestRecent(String searchword) {
        List<Diary> diaries = diaryRepositoryInterface.findByTravelDestContaining(searchword);
        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();

        // 중복 방지를 위한 Set
        Set<Long> addedDiaryIds = new HashSet<>();

        List<Diary> sortedDiaries = diaries.stream()
                .sorted(Comparator.comparing(Diary::getCreatedAt).reversed())
                .collect(Collectors.toList());

        for (Diary diary : sortedDiaries) {
            // 이미 추가한 Diary인지 확인
            if (addedDiaryIds.contains(diary.getDiaryId())) {
                continue; // 중복된 Diary면 스킵
            }

            if (diary.isPublic()) {
                DiaryResponseDto diaryResponseDto = diaryRepository.readDiary(diary.getDiaryId());
                diaryResponseDtos.add(diaryResponseDto);
            }

            // 이미 추가한 Diary로 표시
            addedDiaryIds.add(diary.getDiaryId());
        }

        return diaryResponseDtos;
    }

}
