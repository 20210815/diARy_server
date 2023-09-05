package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.entity.DiaryLocation;
import com.hanium.diARy.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiaryLocationInterface extends CrudRepository<DiaryLocation, Long> {
    public List<DiaryLocation> findByDiary_DiaryId(Long diaryId);
    public List<DiaryLocation> findByAddress(String address);
    public List<DiaryLocation> findByAddressAndDiary_UserOrderByDiaryLikesCountDesc(String address, User user);
    public List<DiaryLocation> findByAddressOrderByDiaryLikesCountDesc(String address);

}
