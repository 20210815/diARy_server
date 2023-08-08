package com.hanium.diARy.diary.service;

import com.hanium.diARy.diary.DiaryMapper;
import com.hanium.diARy.diary.ReplyMapper;
import com.hanium.diARy.diary.dto.CommentDto;
import com.hanium.diARy.diary.entity.Comment;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.repository.*;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentRepositoryInterface commentRepositoryInterface;
    private final DiaryMapper diaryMapper;
    private final ReplyMapper replyMapper;

    @Autowired
    public CommentService(
            CommentRepository commentRepository,
            CommentRepositoryInterface commentRepositoryInterface,
            DiaryMapper diaryMapper,
            ReplyMapper replyMapper

    ) {
        this.commentRepository = commentRepository;
        this.commentRepositoryInterface = commentRepositoryInterface;
        this.diaryMapper = diaryMapper;
        this.replyMapper = replyMapper;
    }

    public void createComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setDiary(this.diaryMapper.toEntity(commentDto.getDiary()));
        comment.setUser(this.convertUserDtoToUser(commentDto.getUser()));
        comment.setContent(commentDto.getContent());
        this.commentRepositoryInterface.save(comment);
    }

    public CommentDto readComment(Long id) {
        Comment comment = this.commentRepository.readComment(id);
        return new CommentDto(
                diaryMapper.toDto(comment.getDiary()),
                comment.getContent(),
                this.convertUserToUserDto(comment.getUser()),
                this.replyMapper.toDtoList(comment.getReplies())
        );
    }

    public List<CommentDto> readCommentAll() {
        Iterator<Comment> iterator = this.commentRepository.readCommentAll();
        List<CommentDto> commentDtoList = new ArrayList<>();

        while (iterator.hasNext()) {
            Comment comment = iterator.next();
            CommentDto dto = new CommentDto();
            dto.setContent(comment.getContent());
            dto.setUser(this.convertUserToUserDto(comment.getUser()));
            dto.setDiary(this.diaryMapper.toDto(comment.getDiary()));
            commentDtoList.add(dto);
        }

        return commentDtoList;
    }

/*    public List<CommentDto> readUserCommentAll(Long userId) {
        List<Comment> commentList = this.commentRepository.readUserCommentAll(userId);
        List<CommentDto> commentDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            commentDtoList.add(new CommentDto(
                    this.diaryMapper.toDto(comment.getDiary()),
                    comment.getContent(),
                    this.convertUserToUserDto(comment.getUser()),
                    this.replyMapper.toDtoList(comment.getReplies())
            ));
        }

        return commentDtoList;
    }*/

    public List<CommentDto> readDiaryCommentAll(Long diaryId) {
        List<Comment> commentList = this.commentRepository.readDiaryCommentAll(diaryId);
        List<CommentDto> commentDtoList = new ArrayList<>();

        for(Comment comment : commentList) {
            commentDtoList.add(new CommentDto(
                    this.diaryMapper.toDto(comment.getDiary()),
                    comment.getContent(),
                    this.convertUserToUserDto(comment.getUser()),
                    this.replyMapper.toDtoList(comment.getReplies())
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

    private User convertUserDtoToUser(UserDto userDto) {
        User user = new User();
        // UserDto에서 필요한 정보를 User 엔티티로 복사하는 로직을 작성
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setImage(userDto.getImage());
        user.setUsername(userDto.getUsername());
        // 추가적인 정보가 있다면 이곳에서 설정

        return user;
    }



    public UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setUsername(user.getUsername());
        // 추가적인 정보가 있다면 이곳에서 설정

        return userDto;
    }
}