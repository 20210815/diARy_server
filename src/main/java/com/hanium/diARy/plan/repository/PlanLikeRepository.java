package com.hanium.diARy.plan.repository;

import com.hanium.diARy.plan.dto.PlanResponseDto;
import com.hanium.diARy.plan.entity.PlanLike;
import com.hanium.diARy.plan.entity.PlanLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanLikeRepository extends JpaRepository<PlanLike, PlanLikeId> {

    @Query("SELECT p.user.userId FROM PlanLike p WHERE p.plan.planId = ?1")
    List<Long> getAllUserIdsLikedByPlan_PlanId(Long planId);

    void deleteByPlan_PlanIdAndUser_UserId(Long planId, Long userId);

    List<PlanLike> findByUser_UserId(Long userId);
}
