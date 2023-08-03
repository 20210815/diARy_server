package com.hanium.diARy.plan.controller;

import com.hanium.diARy.plan.dto.PlanDto;
import com.hanium.diARy.plan.dto.PlanRequestDto;
import com.hanium.diARy.plan.dto.PlanResponseDto;
import com.hanium.diARy.plan.entity.Plan;
import com.hanium.diARy.plan.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plan")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

//    @GetMapping
//    public ResponseEntity<List<PlanDto>> getAllPlans() {
//        List<PlanDto> plans = planService.getAllPlans();
//        return new ResponseEntity<>(plans, HttpStatus.OK);
//    }
    
    @PostMapping
    public ResponseEntity<Long> createPlan(@RequestBody PlanRequestDto request) {
        Long planId = planService.createPlan(request);
        return ResponseEntity.ok(planId);
    }

    @PatchMapping("/{planId}")
    public ResponseEntity<PlanResponseDto> updatePlan(@PathVariable Long planId, @RequestBody PlanRequestDto request) {
        PlanResponseDto updatedPlan = planService.updatePlan(planId, request);
        if (updatedPlan != null) {
            return new ResponseEntity<>(updatedPlan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long planId) {
        planService.deletePlan(planId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{planId}")
    public ResponseEntity<PlanResponseDto> getPlan(@PathVariable Long planId) {
        PlanResponseDto plan = planService.getPlanById(planId);
        if (plan != null) {
            return new ResponseEntity<>(plan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{planId}/isPublic")
    public ResponseEntity<PlanResponseDto> updatePlanIsPublic(@PathVariable Long planId, @RequestBody PlanRequestDto request) {
        boolean isPublic = request.getPlan().isPublic();
        PlanResponseDto updatedPlan = planService.updatePlanIsPublic(planId, isPublic);
        if (updatedPlan == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedPlan);
    }
}
