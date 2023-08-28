package com.hanium.diARy.home.search.controller;

import com.hanium.diARy.diary.dto.DiaryResponseDto;
import com.hanium.diARy.home.search.service.SearchService;
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

    @GetMapping("/search/{searchword}/diary-tag")
    public List<DiaryResponseDto> findDiaryByTag(@PathVariable String searchword){
        return searchService.findDiaryByTag(searchword);
    }
}
