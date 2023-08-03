package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.CommentDto;
import com.hanium.diARy.diary.entity.Comment;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepository;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.Optional;

@Repository
public class CommentRepository {
    private final CommentRepositoryInterface commentRepositoryInterface;
    private final UserRepositoryInterface userRepositoryInterface;
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    public CommentRepository(
            @Autowired CommentRepositoryInterface commentRepositoryInterface,
            @Autowired UserRepositoryInterface userRepositoryInterface,
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface
    ) {
        this.commentRepositoryInterface = commentRepositoryInterface;
        this.userRepositoryInterface = userRepositoryInterface;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
    }
    public void createComment(CommentDto commentDto) {
        Comment comment = new Comment();
        Optional<User> user = userRepositoryInterface.findById(commentDto.getUser().getUserId());
        user.ifPresent(comment::setUser);
        Optional<Diary> diary = diaryRepositoryInterface.findById(commentDto.getDiary().getDiaryId());
        diary.ifPresent(comment::setDiary);

        commentRepositoryInterface.save(comment);
    }

    public Comment readComment(Long id) {
        Optional<Comment> comment = this.commentRepositoryInterface.findById(id);
        if(comment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return comment.get();
    }

    public Iterator<Comment> readCommentAll() {
        return this.commentRepositoryInterface.findAll().iterator();
    }

    public void updateComment(Long id, CommentDto dto) {
        Optional<Comment> targetComment = this.commentRepositoryInterface.findById(id);
        if(targetComment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Comment comment = targetComment.get();
        comment.setContent(
                dto.getContent() == null? comment.getContent() : dto.getContent());
        comment.setDiary(
                dto.getDiary() == null? comment.getDiary() : dto.getDiary());
        comment.setUser(
                dto.getUser() == null? comment.getUser() : dto.getUser());

        this.commentRepositoryInterface.save(comment);
    }

    public void deleteComment(Long id) {
        Optional<Comment> targetComment = this.commentRepositoryInterface.findById(id);
        if(targetComment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        this.commentRepositoryInterface.delete(targetComment.get());
    }
}
