package com.hanium.diARy.plan.repository;

import com.hanium.diARy.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByUser_UserId(Long userId);
}
