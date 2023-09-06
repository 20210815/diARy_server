package com.hanium.diARy.diary.controller;

import com.hanium.diARy.auth.SecurityConfig;
import com.hanium.diARy.diary.dto.DiaryRequestDto;
import com.hanium.diARy.diary.dto.DiaryResponseDto;
import com.hanium.diARy.diary.service.DiaryService;
import com.hanium.diARy.diary.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diary")
public class DiaryController {
    private final DiaryService diaryService;
    private final TagService tagService;
    private final SecurityConfig securityConfig;

    public DiaryController(
            @Autowired DiaryService diaryService,
            @Autowired TagService tagService,
            SecurityConfig securityConfig) {
        this.diaryService = diaryService;
        this.tagService = tagService;
        this.securityConfig = securityConfig;
    }

    @PostMapping()
    public Long createDiary(@RequestBody DiaryRequestDto dto) {
        return this.diaryService.createDiary(dto);
    }

    @GetMapping()
    public List<DiaryResponseDto> readDiaryAll() {
        return this.diaryService.readDiaryAll();
    }

   @GetMapping("/isPublic")
   public List<DiaryResponseDto> readPublicDiaryAll() {return this.diaryService.readPublicDiaryAll();}

    @GetMapping("/{diaryId}")
    public DiaryResponseDto readDiary(@PathVariable("diaryId") Long id) {
        return this.diaryService.readDiary(id);
    }

    @PatchMapping("/{diaryId}")
    public void updateDiary(@PathVariable("diaryId") Long id,
                            @RequestBody DiaryRequestDto diaryDto){
        this.diaryService.updateDiary(id, diaryDto);
    }

    @DeleteMapping("/{diaryId}")
    public void deleteDiary(@PathVariable("diaryId") Long id) {
        this.diaryService.deleteDiary(id);
    }


}
