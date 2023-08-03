package com.hanium.diARy.plan.controller;

import com.hanium.diARy.plan.dto.PlanLikeDto;
import com.hanium.diARy.plan.service.PlanLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlanLikeController {

    private final PlanLikeService planLikeService;

    public PlanLikeController(PlanLikeService planLikeService) {
        this.planLikeService = planLikeService;
    }

    @GetMapping("/plan/{planId}/plan-like")
    public ResponseEntity<List<Long>> getAllPlanLikesByPlanId(@PathVariable Long planId) {
        List<Long> planLikes = planLikeService.getAllUserIdsLikesByPlanId(planId);
        return new ResponseEntity<>(planLikes, HttpStatus.OK);
    }

    @PostMapping("/plan/{planId}/plan-like")
    public ResponseEntity<String> createPlanLike(@PathVariable Long planId, @RequestBody PlanLikeDto planLikeDto) {
        // PlanId를 설정합니다.
        planLikeDto.setPlanId(planId);
        planLikeService.createPlanLike(planId, planLikeDto);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @DeleteMapping("/plan/{planId}/plan-like/{userId}")
    public ResponseEntity<Void> deletePlanLike(@PathVariable Long planId, @PathVariable Long userId) {
        planLikeService.deletePlanLike(planId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}/plan-like")
    public ResponseEntity<List<Long>> getAllPlanIdsLikedByUserId(@PathVariable Long userId) {
        List<Long> planIdsLikedByUser = planLikeService.getAllPlanIdsLikedByUserId(userId);
        return new ResponseEntity<>(planIdsLikedByUser, HttpStatus.OK);
    }
}
