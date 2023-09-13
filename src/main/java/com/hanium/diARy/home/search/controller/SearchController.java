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
@RequestMapping
@ComponentScan
public class SearchController {
    private final SearchService searchService;

    public SearchController(
            @Autowired SearchService searchService
    ) {
        this.searchService = searchService;
    }

    @GetMapping("/search/{searchword}/diary-tag/like")
    public List<DiaryResponseDto> findDiaryByTagLike(@PathVariable String searchword){
        return searchService.findDiaryByTagLike(searchword);
    }

    @GetMapping("/search/{searchword}/plan-tag")
    public List<PlanResponseDto> findPlanByTag(@PathVariable String searchword){
        return searchService.findPlanByTag(searchword);
    }

    @GetMapping("/search/{searchword}/diary-writer/like")
    public List<DiaryResponseDto> findDiaryByWriterLike(@PathVariable String searchword) {
        return searchService.findDiaryByWriterLike(searchword);
    }

    @GetMapping("/search/{searchword}/diary-dest/like")
    public List<DiaryResponseDto> findDiaryByDestLike(@PathVariable String searchword) {
        return searchService.findDiaryByDestLike(searchword);
    }

    @GetMapping("/search/{searchword}/diary-writer/recent")
    public List<DiaryResponseDto> findDiaryByWriterRecent(@PathVariable String searchword) {
        return searchService.findDiaryByWriterRecent(searchword);
    }

    @GetMapping("/search/{searchword}/diary-dest/recent")
    public List<DiaryResponseDto> findDiaryByDestRecent(@PathVariable String searchword) {
        return searchService.findDiaryByDestRecent(searchword);
    }
}
