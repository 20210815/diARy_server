package com.hanium.diARy.plan.repository;

import com.hanium.diARy.plan.entity.PlanLike;
import com.hanium.diARy.plan.entity.PlanLikeId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanLikeRepository extends JpaRepository<PlanLike, PlanLikeId> {
    List<PlanLike> findByPlan_PlanId(Long planId);
//    void deleteById(PlanLike planLike);

//    Optional<PlanLike> findById(PlanLikeId planLikeId);
@Transactional
@Modifying
@Query("DELETE FROM PlanLike p WHERE p.plan.planId = ?1 AND p.user.userId = ?2")
void deleteById(Long planId, Long userId);

}
