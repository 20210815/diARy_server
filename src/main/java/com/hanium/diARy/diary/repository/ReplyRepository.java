package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.ReplyMapper;
import com.hanium.diARy.diary.dto.ReplyDto;
import com.hanium.diARy.diary.entity.Comment;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.Reply;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class ReplyRepository {
    private final ReplyRepositoryInterface replyRepositoryInterface;
    private final CommentRepositoryInterface commentRepositoryInterface;
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final UserRepositoryInterface userRepositoryInterface;
    private final ReplyMapper replyMapper;

    public ReplyRepository(
        @Autowired ReplyRepositoryInterface replyRepositoryInterface,
        @Autowired CommentRepositoryInterface commentRepositoryInterface,
        @Autowired ReplyMapper replyMapper,
        @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
        @Autowired UserRepositoryInterface userRepositoryInterface
        ){
        this.replyRepositoryInterface = replyRepositoryInterface;
        this.commentRepositoryInterface = commentRepositoryInterface;
        this.replyMapper = replyMapper;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.userRepositoryInterface = userRepositoryInterface;

    }

    public void createReply(ReplyDto dto) {
        Reply reply = new Reply();
        Diary diary = this.diaryRepositoryInterface.findById(dto.getDiaryId()).get();
        User user = this.userRepositoryInterface.findById(dto.getUserId()).get();
        Comment comment = this.commentRepositoryInterface.findById(dto.getCommentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        reply.setDiary(diary);
        reply.setComment(comment);
        reply.setUser(user);
        reply.setContent(dto.getContent());
        comment.getReplies().add(this.replyMapper.toEntity(dto));


        this.replyRepositoryInterface.save(reply);
    }

    public Reply readReply(Long id) {
        Optional<Reply> reply = this.replyRepositoryInterface.findById(id);
        if(reply.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return reply.get();
    }

    public Iterator<Reply> readReplyAll() {
        return this.replyRepositoryInterface.findAll().iterator();
    }

    public List<Reply> readCommentReplyAll(Long id) {
        Comment comment = this.commentRepositoryInterface.findByCommentId(id);
        return comment.getReplies();
    }

    public List<Reply> readUserReplyAll(Long id) {
        return this.replyRepositoryInterface.findByUser_UserId(id);
    }

    public void updateReply(Long id, ReplyDto dto) {
        Optional<Reply> targetReply = this.replyRepositoryInterface.findById(id);
        if(targetReply.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Reply reply = targetReply.get();
        reply.setContent(
                dto.getContent() == null? reply.getContent() : dto.getContent()
        );
        reply.setUser(
                this.userRepositoryInterface.findById(dto.getUserId()).get() == null ? this.userRepositoryInterface.findById(reply.getUser().getUserId()).get() : this.userRepositoryInterface.findById(dto.getUserId()).get()
        );
        reply.setComment(
                this.commentRepositoryInterface.findById(dto.getCommentId()).get() == null ? this.commentRepositoryInterface.findById(reply.getComment().getCommentId()).get() : this.commentRepositoryInterface.findById(dto.getCommentId()).get()
        );
        reply.setDiary(
                this.diaryRepositoryInterface.findById(dto.getDiaryId()).get() == null ? this.diaryRepositoryInterface.findById(reply.getDiary().getDiaryId()).get() : this.diaryRepositoryInterface.findById(dto.getDiaryId()).get()
        );
        this.replyRepositoryInterface.save(reply);
    }

    public void deleteReply(Long id) {
        Optional<Reply> targetReply = this.replyRepositoryInterface.findById(id);
        if(targetReply.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        this.replyRepositoryInterface.delete(targetReply.get());
    }

}
