package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepositoryInterface extends CrudRepository<Comment, Long> {

    public List<Comment> findByDiary_DiaryId(Long diaryId);
    public List<Comment> findByUser_UserId(Long userId);
}
