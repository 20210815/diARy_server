package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.entity.Comment;
import com.hanium.diARy.diary.entity.Reply;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ReplyRepositoryInterface extends CrudRepository<Reply, Long> {
    public List<Reply> findByComment(Comment comment);
    public List<Reply> findByUser_UserId(Long id);
}
