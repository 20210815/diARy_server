package com.hanium.diARy.diary.controller;
import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.dto.DiaryLikeDto;
import com.hanium.diARy.diary.entity.DiaryLikeId;
import com.hanium.diARy.diary.service.DiaryLikeService;
import com.hanium.diARy.diary.service.DiaryService;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diary")
public class DiaryLikeController {
    private final DiaryLikeService diaryLikeService;
    private final DiaryService diaryService;

    @Autowired
    public DiaryLikeController(DiaryLikeService diaryLikeService,
                               DiaryService diaryService) {
        this.diaryLikeService = diaryLikeService;
        this.diaryService = diaryService;
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
    public List<User> readDiaryLikeAllByDiary(@PathVariable("diaryId") Long diaryId) {
        return this.diaryLikeService.getUsersWhoLikedDiaryId(diaryId);
    }
/*    @GetMapping("/user/{userId}/diary-like")
    public List<DiaryDto> readDiaryLikeAllByUser(@PathVariable("userId") Long userId) {
        List<DiaryDto> diaryLikeDtoList = diaryLikeService.getLikedDiariesByUserId(userId);
        return diaryLikeDtoList;
    }*/


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