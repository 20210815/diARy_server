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

    @PostMapping("/{diaryId}/diary-like")
    public void createDiaryLike(@RequestBody DiaryLikeDto dto) {
        diaryLikeService.createDiaryLike(dto);
    }

/*    @GetMapping("/{diaryId}/{userId}")
    public DiaryLikeDto readDiaryLike(@PathVariable Long diaryId, @PathVariable Long userId) {
        DiaryLikeIdDto idDto = new DiaryLikeIdDto(userId, diaryId);
        DiaryLikeDto diaryLikeDto = diaryLikeService.readDiaryLike(idDto);
        return diaryLikeDto;
    }*/

    @GetMapping("/{diaryId}/diary-like")
    public List<DiaryLikeDto> readDiaryLikeAllByDiary(@PathVariable Long diaryId) {
        List<DiaryLikeDto> diaryLikeDtoList = diaryLikeService.getUsersWhoLikedDiaryId(diaryId);
        return diaryLikeDtoList;
    }
    @GetMapping("/{userId}/diary-like")
    public List<DiaryLikeDto> readDiaryLikeAllByUser(@PathVariable Long userId) {
        List<DiaryLikeDto> diaryLikeDtoList = diaryLikeService.getLikedDiariesByUserId(userId);
        return diaryLikeDtoList;
    }


/*    @PutMapping("/{diaryId}/{userId}")
    public void updateDiaryLike(@PathVariable DiaryLikeId id,
                                                @RequestBody DiaryLikeDto dto) {
        diaryLikeService.updateDiaryLike(id, dto);
    }*/

    @DeleteMapping("/{diaryId}/diary-like/{userId}")
    public void deleteDiaryLike(@PathVariable Long diaryId,
                                @PathVariable Long userId)
    {
        DiaryLikeId id = new DiaryLikeId(diaryId, userId);
        diaryLikeService.deleteDiaryLike(id);
    }
}