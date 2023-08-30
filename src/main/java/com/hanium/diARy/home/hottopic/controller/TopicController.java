package com.hanium.diARy.home.hottopic.controller;

import com.hanium.diARy.diary.dto.DiaryResponseDto;
import com.hanium.diARy.home.hottopic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class TopicController {
    private final TopicService topicService;

    public TopicController(
            @Autowired TopicService topicService
    ) {
        this.topicService = topicService;
    }

    @GetMapping("/topic")
    public List<DiaryResponseDto> readAllDiaryOrderByLike() {
        return topicService.readAllDiaryOrderByLike();
    }

}
