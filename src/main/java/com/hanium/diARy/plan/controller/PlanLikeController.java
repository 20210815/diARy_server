package com.hanium.diARy.plan.controller;

import com.hanium.diARy.plan.dto.PlanLikeDto;
import com.hanium.diARy.plan.service.PlanLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/plan/{planId}/plan-like")
public class PlanLikeController {

    private final PlanLikeService planLikeService;

    public PlanLikeController(PlanLikeService planLikeService) {
        this.planLikeService = planLikeService;
    }

    @GetMapping
    public ResponseEntity<List<Long>> getAllPlanLikesByPlanId(@PathVariable Long planId) {
        List<Long> planLikes = planLikeService.getAllUserIdsLikesByPlanId(planId);
        return new ResponseEntity<>(planLikes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createPlanLike(@PathVariable Long planId, @RequestBody PlanLikeDto planLikeDto) {
        // PlanId를 설정합니다.
        planLikeDto.setPlanId(planId);
        planLikeService.createPlanLike(planId, planLikeDto);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deletePlanLike(@PathVariable Long planId, @PathVariable Long userId) {
        planLikeService.deletePlanLike(planId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
