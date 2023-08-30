package com.hanium.diARy.plan.repository;

import com.hanium.diARy.plan.entity.PlanTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanTagRepository extends JpaRepository<PlanTag, Long> {
    PlanTag findByName(String name);
//    List<PlanTag> findByPlan_PlanId(Long planId);
}
