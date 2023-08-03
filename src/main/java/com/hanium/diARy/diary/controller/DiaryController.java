package com.hanium.diARy.diary.controller;

import com.hanium.diARy.diary.service.DiaryService;
import com.hanium.diARy.diary.dto.DiaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("diary")
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

    @GetMapping("{id}")
    public DiaryDto readDiary(@PathVariable("id") Long id) {
        return this.diaryService.readDiary(id);
    }

    @PutMapping("{id}")
    public void updateDiary(@PathVariable("id") Long id,
                            @RequestBody DiaryDto diaryDto){
        this.diaryService.updateDiary(id, diaryDto);
    }

    @DeleteMapping("{id}")
    public void deleteDiary(@PathVariable("id") Long id) {
        this.diaryService.deleteDiary(id);
    }


}
