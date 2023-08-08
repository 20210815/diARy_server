package com.hanium.diARy.plan.repository;

import com.hanium.diARy.plan.entity.PlanLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanLocationRepository extends JpaRepository<PlanLocation, Long> {
    List<PlanLocation> findByPlan_PlanId(Long planId);
}
