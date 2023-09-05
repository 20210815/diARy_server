package com.hanium.diARy.plan.repository;

import com.hanium.diARy.plan.entity.Plan;
import com.hanium.diARy.plan.entity.PlanTagMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByUser_UserId(Long userId);
    @Query("SELECT ptm.plan.planId FROM PlanTagMap ptm WHERE ptm.planTag.tagId = :tagId")
    List<Long> findPlanIdsByTagId(@Param("tagId") Long tagId);

}
