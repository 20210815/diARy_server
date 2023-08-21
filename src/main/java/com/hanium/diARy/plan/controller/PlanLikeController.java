package com.hanium.diARy.plan.controller;

import com.hanium.diARy.plan.dto.PlanLikeDto;
import com.hanium.diARy.plan.service.PlanLikeService;
import com.hanium.diARy.user.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plan/{planId}/plan-like")
public class PlanLikeController {

    private final PlanLikeService planLikeService;

    public PlanLikeController(PlanLikeService planLikeService) {
        this.planLikeService = planLikeService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllPlanLikesByPlanId(@PathVariable Long planId) {
        List<UserDto> planLikes = planLikeService.getAllUserIdsLikesByPlanId(planId);
        return new ResponseEntity<>(planLikes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createPlanLike(@PathVariable Long planId, @RequestBody PlanLikeDto planLikeDto) {
        // PlanId를 설정합니다.
        planLikeDto.setPlanId(planId);
        planLikeService.createPlanLike(planId, planLikeDto);
        return new ResponseEntity<>("좋아요", HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deletePlanLike(@PathVariable Long planId, @PathVariable Long userId) {
        planLikeService.deletePlanLike(planId, userId);
        return new ResponseEntity<>("좋아요 취소", HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getPlanLikeCount(@PathVariable Long planId) {
        Long planLikeCount = planLikeService.getPlanLikeCount(planId);
        return new ResponseEntity<>(planLikeCount, HttpStatus.OK);
    }
}
