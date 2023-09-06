package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.entity.Address;
import com.hanium.diARy.diary.entity.DiaryLocation;
import com.hanium.diARy.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiaryLocationInterface extends CrudRepository<DiaryLocation, Long> {
    public List<DiaryLocation> findByDiary_DiaryId(Long diaryId);
    public List<DiaryLocation> findByXAndYAndDiary_UserOrderByDiaryLikesCountDesc(String x, String y, User user);
    public List<DiaryLocation> findByXAndYOrderByDiaryLikesCountDesc(String x, String y);
    public List<DiaryLocation> findByXAndY(String x, String y);

}
