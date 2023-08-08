package com.hanium.diARy.diary.controller;

import com.hanium.diARy.diary.dto.DiaryRequestDto;
import com.hanium.diARy.diary.dto.DiaryResponseDto;
import com.hanium.diARy.diary.service.DiaryService;
import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.service.TagService;
import com.hanium.diARy.plan.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diary")
public class DiaryController {
    private final DiaryService diaryService;
    private final TagService tagService;

    public DiaryController(
            @Autowired DiaryService diaryService,
            @Autowired TagService tagService
    ) {
        this.diaryService = diaryService;
        this.tagService = tagService;
    }

    @PostMapping()
    public Long createDiary(@RequestBody DiaryRequestDto diaryDto) {
        return this.diaryService.createDiary(diaryDto);
    }

    @GetMapping()
    public List<DiaryResponseDto> readDiaryAll() {
        return this.diaryService.readDiaryAll();
    }

   /// @GetMapping("/public")
   // public List<DiaryResponseDto> readPublicDiaryAll() {return this.diaryService.readPublicDiaryAll();}

    @GetMapping("/{diaryId}")
    public DiaryResponseDto readDiary(@PathVariable("diaryId") Long id) {
        return this.diaryService.readDiary(id);
    }

    @PutMapping("/{diaryId}")
    public void updateDiary(@PathVariable("diaryId") Long id,
                            @RequestBody DiaryRequestDto diaryDto){
        this.diaryService.updateDiary(id, diaryDto);
    }

    @DeleteMapping("/{diaryId}")
    public void deleteDiary(@PathVariable("diaryId") Long id) {
        this.diaryService.deleteDiary(id);
    }


}
