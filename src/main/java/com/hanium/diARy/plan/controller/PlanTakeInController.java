package com.hanium.diARy.plan.controller;

import com.hanium.diARy.plan.service.PlanTakeInService;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plan/{planId}/take-in")
public class PlanTakeInController {
    private final PlanTakeInService planTakeInService;
    private final UserRepositoryInterface userRepositoryInterface;

    public PlanTakeInController(PlanTakeInService planTakeInService, UserRepositoryInterface userRepositoryInterface) {
        this.planTakeInService = planTakeInService;
        this.userRepositoryInterface = userRepositoryInterface;
    }

    @PostMapping
    public ResponseEntity<String> createPlanTakeIn(@PathVariable Long planId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepositoryInterface.findByEmail(userEmail);
        Long userId = user.getUserId();

        planTakeInService.createPlanTakeIn(planId, userId);
        return new ResponseEntity<>("일정 담기", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deletePlanTakeIn(@PathVariable Long planId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepositoryInterface.findByEmail(userEmail).getUserId();

        planTakeInService.deletePlanTakeIn(planId, userId);
        return new ResponseEntity<>("일정 담기 취소", HttpStatus.OK);
    }

}
