package com.hanium.diARy.home.hottopic.controller;

import com.hanium.diARy.diary.dto.DiaryResponseDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryTag;
import com.hanium.diARy.diary.repository.TagRepository;
import com.hanium.diARy.home.hottopic.dto.HottopicDto;
import com.hanium.diARy.home.hottopic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.HTML;
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
    public List<HottopicDto> readAllDiaryOrderByLike() {
        return topicService.readBestDiaryTag();
    }

//    @GetMapping("/best")
//    public List<DiaryTag> getBestDiaryTag() {
//        return topicService.readBestDiaryTag();
//    }

}
