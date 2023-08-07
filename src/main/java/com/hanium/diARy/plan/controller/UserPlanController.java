package com.hanium.diARy.plan.controller;

import com.hanium.diARy.plan.dto.PlanResponseDto;
import com.hanium.diARy.plan.service.PlanLikeService;
import com.hanium.diARy.plan.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/user/{userId}")
public class UserPlanController {

    private final PlanService planService;
    private final PlanLikeService planLikeService;

    public UserPlanController(PlanService planService, PlanLikeService planLikeService) {
        this.planService = planService;
        this.planLikeService = planLikeService;
    }

    @GetMapping("/plan")
    public ResponseEntity<List<PlanResponseDto>> getAllPlanByUserId(@PathVariable Long userId) {
        List<PlanResponseDto> planIdsByUser = planService.getAllPlanByUserId(userId);
        return new ResponseEntity<>(planIdsByUser, HttpStatus.OK);
    }

    @GetMapping("/plan-like")
    public ResponseEntity<List<PlanResponseDto>> getAllPlanLikedByUserId(@PathVariable Long userId) {
        List<PlanResponseDto> planIdsLikedByUser = planLikeService.getAllPlanLikedByUserId(userId);
        return new ResponseEntity<>(planIdsLikedByUser, HttpStatus.OK);
    }
}
