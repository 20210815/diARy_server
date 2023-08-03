package com.hanium.diARy.plan.repository;

import com.hanium.diARy.plan.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByPlan_PlanId(Long planId);
}
