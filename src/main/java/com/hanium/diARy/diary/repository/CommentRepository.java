package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.CommentDto;
import com.hanium.diARy.diary.entity.Comment;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.user.UserMapper;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepository;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Repository
public class CommentRepository {
    private final CommentRepositoryInterface commentRepositoryInterface;
    private final UserRepository userRepository;
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final ReplyRepository replyRepository;
    private final UserRepositoryInterface userRepositoryInterface;
    public CommentRepository(
            @Autowired CommentRepositoryInterface commentRepositoryInterface,
            @Autowired UserRepository userRepository,
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired ReplyRepository replyRepository,
            @Autowired UserRepositoryInterface userRepositoryInterface
    ) {
        this.commentRepositoryInterface = commentRepositoryInterface;
        this.userRepository = userRepository;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.replyRepository = replyRepository;
        this.userRepositoryInterface = userRepositoryInterface;
    }

    @Transactional
    public void createComment(CommentDto commentDto, Long diaryId) {
        Comment comment = new Comment();
        Diary diary = this.diaryRepositoryInterface.findById(diaryId).get();

//        User user = new User();
//        user.setUserId(1L);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepositoryInterface.findByEmail(email);
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

/*
    public List<CommentReplyDto> readUserCommentAll(Long userId) {
        List<CommentReplyDto> commentReplyDtoList = new ArrayList<>();

        this.commentRepositoryInterface.findByUser_UserId(userId);




        User user = this.userRepositoryInterface.findById(userId).get();
        List<Comment> commentList = this.commentRepositoryInterface.findByUser(user);
        // Reply도 해당 User만 읽히도록 해야 함
        for (Comment comment : commentList) {
            List<Reply> replies = comment.getReplies();
        }
        return commentList;
    }
*/

    public List<CommentDto> readDiaryCommentAll(Long diaryId) {
        Diary diary = this.diaryRepositoryInterface.findById(diaryId).get();
        List<CommentDto> commentDtoList = new ArrayList<>();
        for(Comment comment: this.commentRepositoryInterface.findByDiary(diary)) {
            CommentDto commentDto = new CommentDto();
            commentDto.setDiaryId(comment.getDiary().getDiaryId());
            commentDto.setReplyDtos(replyRepository.readCommentReplyAll(comment.getCommentId()));//해야 함
            commentDto.setContent(comment.getContent());
            commentDto.setUpdatedAt(comment.getUpdatedAt());
            commentDto.setCreatedAt(comment.getCreatedAt());
            //userDto넣기
            commentDto.setUserDto(userRepository.makeUserDto(comment.getUser().getUserId()));
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }

    public void updateComment(Long id, CommentDto dto) {
        Comment comment = this.commentRepositoryInterface.findById(id).get();
        comment.setContent(dto.getContent());
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
