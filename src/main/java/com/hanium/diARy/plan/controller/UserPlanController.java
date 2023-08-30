package com.hanium.diARy.plan.controller;

import com.hanium.diARy.plan.dto.PlanResponseDto;
import com.hanium.diARy.plan.service.PlanLikeService;
import com.hanium.diARy.plan.service.PlanService;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import com.hanium.diARy.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserPlanController {

    private final PlanService planService;
    private final PlanLikeService planLikeService;
    private final UserRepositoryInterface userRepositoryInterface;

    public UserPlanController(PlanService planService, PlanLikeService planLikeService, UserRepositoryInterface userRepositoryInterface) {
        this.planService = planService;
        this.planLikeService = planLikeService;
        this.userRepositoryInterface = userRepositoryInterface;
    }

    @GetMapping("/plan")
    public ResponseEntity<List<PlanResponseDto>> getAllPlanByUserId() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepositoryInterface.findByEmail(userEmail).getUserId();
        List<PlanResponseDto> planIdsByUser = planService.getAllPlanByUserId(userId);
        return new ResponseEntity<>(planIdsByUser, HttpStatus.OK);
    }

    @GetMapping("/plan-like")
    public ResponseEntity<List<PlanResponseDto>> getAllPlanLikedByUserId() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepositoryInterface.findByEmail(userEmail).getUserId();
        List<PlanResponseDto> planIdsLikedByUser = planLikeService.getAllPlanLikedByUserId(userId);
        return new ResponseEntity<>(planIdsLikedByUser, HttpStatus.OK);
    }
}
