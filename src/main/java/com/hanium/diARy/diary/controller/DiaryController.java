package com.hanium.diARy.diary.controller;

import com.hanium.diARy.diary.service.DiaryService;
import com.hanium.diARy.diary.dto.DiaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diary")
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(
            @Autowired DiaryService diaryService
    ) {
        this.diaryService = diaryService;
    }

    @PostMapping()
    public void createDiary(@RequestBody DiaryDto diaryDto) {
        this.diaryService.createDiary(diaryDto);
    }

    @GetMapping()
    public List<DiaryDto> readDiaryAll() {
        return this.diaryService.readDiaryAll();
    }

    @GetMapping("/{diaryId}")
    public DiaryDto readDiary(@PathVariable("diaryId") Long id) {
        return this.diaryService.readDiary(id);
    }

    @PutMapping("/{diaryId}")
    public void updateDiary(@PathVariable("diaryId") Long id,
                            @RequestBody DiaryDto diaryDto){
        this.diaryService.updateDiary(id, diaryDto);
    }

    @DeleteMapping("/{diaryId}")
    public void deleteDiary(@PathVariable("diaryId") Long id) {
        this.diaryService.deleteDiary(id);
    }


}
