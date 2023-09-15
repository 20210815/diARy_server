package com.hanium.diARy.rank.controller;

import com.hanium.diARy.plan.dto.PlanResponseDto;
import com.hanium.diARy.rank.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rank")
public class RankController {
    private final RankService rankService;

    public RankController(
            @Autowired RankService rankService
    ) {
        this.rankService = rankService;
    }

    @GetMapping("/like")
    public List<PlanResponseDto> RankLike() {
        return rankService.findPlanByLikeRank();
    }

    @GetMapping("/recent")
    public List<PlanResponseDto> RankRecent() {
        return rankService.findPlanByRecentRank();
    }
}
