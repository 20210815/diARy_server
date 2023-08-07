package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.ReplyDto;
import com.hanium.diARy.diary.entity.Comment;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.List;

public interface CommentRepositoryInterface extends CrudRepository<Comment, Long> {

    public List<Comment> findByDiary(Diary diary);
    public List<Comment> findByUser(User user);
    public Comment findByCommentId(Long commentId);
}
