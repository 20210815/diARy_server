package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryLike;
import com.hanium.diARy.diary.entity.DiaryLikeId;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiaryLikeRepositoryInterface extends CrudRepository<DiaryLike, DiaryLikeId> {
    public List<Diary> findByUser_UserId(Long userId);
    public List<User> findByDiary_DiaryId(Long diaryId);
}
