package com.hanium.diARy.plan.repository;

import com.hanium.diARy.plan.entity.PlanTag;
import com.hanium.diARy.plan.entity.PlanTagMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanTagMapRepository extends JpaRepository<PlanTagMap, Long> {
}
