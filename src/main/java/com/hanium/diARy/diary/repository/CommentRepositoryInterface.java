package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.entity.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepositoryInterface extends CrudRepository<Comment, Long> {
}
