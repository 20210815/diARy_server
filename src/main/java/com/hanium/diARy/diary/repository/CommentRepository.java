package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.DiaryMapper;
import com.hanium.diARy.diary.dto.CommentDto;
import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.entity.Comment;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.user.UserMapper;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepository;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Repository
public class CommentRepository {
    private final CommentRepositoryInterface commentRepositoryInterface;
    private final UserRepositoryInterface userRepositoryInterface;
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final UserMapper userMapper;
    private final DiaryMapper diaryMapper;
    public CommentRepository(
            @Autowired CommentRepositoryInterface commentRepositoryInterface,
            @Autowired UserRepositoryInterface userRepositoryInterface,
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired UserMapper userMapper,
            @Autowired DiaryMapper diaryMapper
    ) {
        this.commentRepositoryInterface = commentRepositoryInterface;
        this.userRepositoryInterface = userRepositoryInterface;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.userMapper = userMapper;
        this.diaryMapper = diaryMapper;
    }

    @Transactional
    public void createComment(CommentDto commentDto) {
        Comment comment = new Comment();
        User user = this.userRepositoryInterface.findById(commentDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + commentDto.getUserId()));
        Diary diary = this.diaryRepositoryInterface.findById(commentDto.getDiaryId())
                .orElseThrow(() -> new EntityNotFoundException("Diary not found with ID: " + commentDto.getDiaryId()));

        comment.setUser(user);
        comment.setDiary(diary);
        comment.setContent(commentDto.getContent());
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

    public List<Comment> readUserCommentAll(Long userId) {
        User user = this.userRepositoryInterface.findById(userId).get();
        return this.commentRepositoryInterface.findByUser(user);
    }

    public List<Comment> readDiaryCommentAll(Long diaryId) {
        Diary diary = this.diaryRepositoryInterface.findById(diaryId).get();
        return this.commentRepositoryInterface.findByDiary(diary);
    }

    public void updateComment(Long id, CommentDto dto) {
        Optional<Comment> targetComment = this.commentRepositoryInterface.findById(id);
        if (targetComment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Comment comment = targetComment.get();
        String content = dto.getContent();
        if (content != null) {
            comment.setContent(content);
        }

        Diary diary = this.diaryRepositoryInterface.findById(dto.getDiaryId()).get();
        if(diary != null) {
            comment.setDiary(diary);
        }
        else {
            comment.setDiary(targetComment.get().getDiary());
        }

        User user = this.userRepositoryInterface.findById(dto.getUserId()).get();
        if(user != null) {
            comment.setUser(user);
        }
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
