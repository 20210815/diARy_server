package com.hanium.diARy.diary.service;

import com.hanium.diARy.diary.ReplyMapper;
import com.hanium.diARy.diary.dto.ReplyDto;
import com.hanium.diARy.diary.entity.Comment;
import com.hanium.diARy.diary.entity.Reply;
import com.hanium.diARy.diary.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final ReplyMapper replyMapper;

    @Autowired
    public ReplyService(ReplyRepository replyRepository,
                        ReplyMapper replyMapper) {
        this.replyRepository = replyRepository;
        this.replyMapper = replyMapper;
    }

    public void createReply(ReplyDto dto) {
        replyRepository.createReply(dto);
    }

/*    public ReplyDto readReply(Long id) {
        Reply reply = replyRepository.readReply(id);
        return new ReplyDto(
                reply.getComment(),
                reply.getDiary(),
                reply.getContent(),
                reply.getUser()
        );
    }*/

    public List<ReplyDto> readReplyAll() {
        Iterator<Reply> iterator = replyRepository.readReplyAll();
        List<ReplyDto> replyDtoList = new ArrayList<>();

        while (iterator.hasNext()) {
            Reply reply = iterator.next();
            replyDtoList.add(this.replyMapper.toDto(reply));
        }

        return replyDtoList;
    }

/*    public List<ReplyDto> readUserReplyAll(Long id) {
        List<Reply> replyList = replyRepository.readUserReplyAll(id);
        List<ReplyDto> replyDtoList = new ArrayList<>();

        for(Reply reply : replyList) {
            replyDtoList.add(new ReplyDto(
                    reply.getComment(),
                    reply.getDiary(),
                    reply.getContent(),
                    reply.getUser()

            ));
        }
        return replyDtoList;
    }*/

    public List<ReplyDto> readCommentReplyAll(Long id) {
        List<Reply> replies = this.replyRepository.readCommentReplyAll(id);
        List<ReplyDto> replyDtoList = new ArrayList<>();

        for(Reply reply : replies) {
            replyDtoList.add(replyMapper.toDto(reply));
        }
        return replyDtoList;
    }

    public void updateReply(Long id, ReplyDto dto) {
        replyRepository.updateReply(id, dto);
    }

    public void deleteReply(Long id) {
        replyRepository.deleteReply(id);
    }
}