package com.hanium.diARy.plan.repository;

import com.hanium.diARy.plan.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByPlan_PlanId(Long planId);
}
