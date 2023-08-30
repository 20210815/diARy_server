package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.List;

public interface DiaryRepositoryInterface extends CrudRepository<Diary, Long> {
    public List<Diary> findByIsPublicTrue();
    public List<Diary> findByUser(User user);
    public List<Diary> findByUser_UserId(Long userId);
    public List<Diary> findByIsPublicTrueOrderByLikesCountDesc();
}
