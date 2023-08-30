package com.hanium.diARy.plan.controller;

import com.hanium.diARy.plan.dto.PlanLikeDto;
import com.hanium.diARy.plan.service.PlanLikeService;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plan/{planId}/plan-like")
public class PlanLikeController {

    private final PlanLikeService planLikeService;
    private final UserRepositoryInterface userRepositoryInterface;

    public PlanLikeController(PlanLikeService planLikeService, UserRepositoryInterface userRepositoryInterface) {
        this.planLikeService = planLikeService;
        this.userRepositoryInterface = userRepositoryInterface;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllPlanLikesByPlanId(@PathVariable Long planId) {
        List<UserDto> planLikes = planLikeService.getAllUserIdsLikesByPlanId(planId);
        return new ResponseEntity<>(planLikes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createPlanLike(@PathVariable Long planId) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepositoryInterface.findByEmail(userEmail).getUserId();

        planLikeService.createPlanLike(planId, userId);
        return new ResponseEntity<>("좋아요", HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deletePlanLike(@PathVariable Long planId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepositoryInterface.findByEmail(userEmail).getUserId();

        planLikeService.deletePlanLike(planId, userId);
        return new ResponseEntity<>("좋아요 취소", HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getPlanLikeCount(@PathVariable Long planId) {
        Long planLikeCount = planLikeService.getPlanLikeCount(planId);
        return new ResponseEntity<>(planLikeCount, HttpStatus.OK);
    }
}
