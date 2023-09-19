package com.hanium.diARy.plan.repository;

import com.hanium.diARy.plan.entity.PlanTakeIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanTakeInRepository extends JpaRepository<PlanTakeIn, Long> {
    void deleteByPlan_PlanIdAndUser_UserId(Long planId, Long userId);

    List<PlanTakeIn> findByPlan_PlanId(Long planId);
}
