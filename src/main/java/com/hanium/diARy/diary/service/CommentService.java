package com.hanium.diARy.diary.service;

import com.hanium.diARy.diary.dto.CommentDto;
import com.hanium.diARy.diary.entity.Comment;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.repository.CommentRepository;
import com.hanium.diARy.diary.repository.DiaryRepository;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    @Autowired
    public CommentService(
            CommentRepository commentRepository,
            UserRepository userRepository,
            DiaryRepository diaryRepository
    ) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.diaryRepository = diaryRepository;
    }

    public void createComment(CommentDto commentDto) {
        Comment comment = new Comment();
        Diary diary = diaryRepository.readDiary(commentDto.getDiary().getDiaryId());
        comment.setDiary(diary);
        comment.setUser(commentDto.getUser());
        comment.setContent(commentDto.getContent());
    }

    public CommentDto readComment(Long id) {
        Comment comment = this.commentRepository.readComment(id);
        return new CommentDto(
                comment.getDiary(),
                comment.getContent(),
                comment.getUser()
        );
    }

    public List<CommentDto> readCommentAll() {
        Iterator<Comment> iterator = this.commentRepository.readCommentAll();
        List<CommentDto> commentDtoList = new ArrayList<>();

        while(iterator.hasNext()) {
            Comment comment = iterator.next();
            commentDtoList.add(new CommentDto(
                    comment.getDiary(),
                    comment.getContent(),
                    comment.getUser()
            ));
        }
        return commentDtoList;
    }

    public List<CommentDto> readUserCommentAll(Long userId) {
        List<Comment> commentList = this.commentRepository.readUserCommentAll(userId);
        List<CommentDto> commentDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            commentDtoList.add(new CommentDto(
                    comment.getDiary(),
                    comment.getContent(),
                    comment.getUser()
            ));
        }
        return commentDtoList;
    }

    public List<CommentDto> readDiaryCommentAll(Long diaryId) {
        List<Comment> commentList = this.commentRepository.readDiaryCommentAll(diaryId);
        List<CommentDto> commentDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            commentDtoList.add(new CommentDto(
                    comment.getDiary(),
                    comment.getContent(),
                    comment.getUser()
            ));
        }
        return commentDtoList;
    }

    public void updateComment(Long id, CommentDto dto) {
        this.commentRepository.updateComment(id, dto);
    }

    public void deleteComment(Long id) {
        this.commentRepository.deleteComment(id);
    }
}