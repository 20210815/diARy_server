package com.hanium.diARy.home.search.controller;

import com.hanium.diARy.diary.dto.DiaryResponseDto;
import com.hanium.diARy.home.search.service.SearchService;
import com.hanium.diARy.plan.dto.PlanResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@ComponentScan
public class SearchController {
    private final SearchService searchService;

    public SearchController(
            @Autowired SearchService searchService
    ) {
        this.searchService = searchService;
    }

    @GetMapping("/{searchword}/diary-tag/like")
    public List<DiaryResponseDto> findDiaryByTagLike(@PathVariable String searchword){
        return searchService.findDiaryByTagLike(searchword);
    }

    @GetMapping("/{searchword}/diary-tag/recent")
    public List<DiaryResponseDto> findDiaryByTagRecent(@PathVariable String searchword){
        return searchService.findDiaryByTagRecent(searchword);
    }

    @GetMapping("/{searchword}/diary-writer/like")
    public List<DiaryResponseDto> findDiaryByWriterLike(@PathVariable String searchword) {
        return searchService.findDiaryByWriterLike(searchword);
    }

    @GetMapping("/{searchword}/diary-dest/like")
    public List<DiaryResponseDto> findDiaryByDestLike(@PathVariable String searchword) {
        return searchService.findDiaryByDestLike(searchword);
    }

    @GetMapping("/{searchword}/diary-writer/recent")
    public List<DiaryResponseDto> findDiaryByWriterRecent(@PathVariable String searchword) {
        return searchService.findDiaryByWriterRecent(searchword);
    }

    @GetMapping("/{searchword}/diary-dest/recent")
    public List<DiaryResponseDto> findDiaryByDestRecent(@PathVariable String searchword) {
        return searchService.findDiaryByDestRecent(searchword);
    }

    @GetMapping("/{searchword}/plan-tag/like")
    public List<PlanResponseDto> findPlanByTagLike(@PathVariable String searchword){
        return searchService.findPlanByTagLike(searchword);
    }

    @GetMapping("/{searchword}/plan-tag/recent")
    public List<PlanResponseDto> findPlanByTagRecent(@PathVariable String searchword){
        return searchService.findPlanByTagRecent(searchword);
    }

    @GetMapping("/{searchword}/plan-writer/like")
    public List<PlanResponseDto> findPlanByWriterLike(@PathVariable String searchword){
        return searchService.findPlanByWriterLike(searchword);
    }

    @GetMapping("/{searchword}/plan-writer/recent")
    public List<PlanResponseDto> findPlanByWriterRecent(@PathVariable String searchword){
        return searchService.findPlanByWriterRecent(searchword);
    }

    @GetMapping("/{searchword}/plan-dest/like")
    public List<PlanResponseDto> findPlanByDestLike(@PathVariable String searchword){
        return searchService.findPlanByDestLike(searchword);
    }

    @GetMapping("/{searchword}/plan-dest/recent")
    public List<PlanResponseDto> findPlanByDestRecent(@PathVariable String searchword){
        return searchService.findPlanByDestRecent(searchword);
    }

}
