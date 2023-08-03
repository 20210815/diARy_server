package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.ReplyDto;
import com.hanium.diARy.diary.entity.Reply;
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

    public ReplyRepository(
        @Autowired ReplyRepositoryInterface replyRepositoryInterface){
        this.replyRepositoryInterface = replyRepositoryInterface;
    }

    public void createReply(ReplyDto dto) {
        Reply reply = new Reply();
        reply.setComment(dto.getComment());
        reply.setUser(dto.getUser());
        reply.setDiary(dto.getDiary());
        reply.setContent(dto.getContent());
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
        return this.replyRepositoryInterface.findByComment_CommentId(id);
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
                dto.getUser() == null? reply.getUser() : dto.getUser()
        );
        reply.setComment(
                dto.getComment() == null? reply.getComment() : dto.getComment()
        );
        reply.setDiary(
                dto.getDiary() == null? reply.getDiary() : dto.getDiary()
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
