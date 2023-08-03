package com.hanium.diARy.diary.controller;
import com.hanium.diARy.diary.dto.DiaryLikeDto;
import com.hanium.diARy.diary.entity.DiaryLikeId;
import com.hanium.diARy.diary.service.DiaryLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diary")
public class DiaryLikeController {
    private final DiaryLikeService diaryLikeService;

    @Autowired
    public DiaryLikeController(DiaryLikeService diaryLikeService) {
        this.diaryLikeService = diaryLikeService;
    }

    @PostMapping("/{diary_id}/diary-like")
    public void createDiaryLike(@RequestBody DiaryLikeDto dto) {
        diaryLikeService.createDiaryLike(dto);
    }

/*    @GetMapping("/{diaryId}/{userId}")
    public DiaryLikeDto readDiaryLike(@PathVariable Long diaryId, @PathVariable Long userId) {
        DiaryLikeIdDto idDto = new DiaryLikeIdDto(userId, diaryId);
        DiaryLikeDto diaryLikeDto = diaryLikeService.readDiaryLike(idDto);
        return diaryLikeDto;
    }*/

    @GetMapping("/{diary_id}/diary-like")
    public List<DiaryLikeDto> readDiaryLikeAll(@PathVariable Long diaryId) {
        List<DiaryLikeDto> diaryLikeDtoList = diaryLikeService.readDiaryLikeAll();
        return diaryLikeDtoList;
    }

    @PutMapping("/{diaryId}/{userId}")
    public void updateDiaryLike(@PathVariable DiaryLikeId id,
                                                @RequestBody DiaryLikeDto dto) {
        diaryLikeService.updateDiaryLike(id, dto);
    }

    @DeleteMapping("/{diaryId}/{userId}")
    public void deleteDiaryLike(@PathVariable DiaryLikeId id) {
        diaryLikeService.deleteDiaryLike(id);
    }
}