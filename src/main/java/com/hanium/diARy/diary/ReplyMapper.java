package com.hanium.diARy.diary;

import com.hanium.diARy.diary.dto.ReplyDto;
import com.hanium.diARy.diary.entity.Reply;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class ReplyMapper {

    private final ModelMapper modelMapper;

    public ReplyMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ReplyDto toDto(Reply reply) {
        return modelMapper.map(reply, ReplyDto.class);
    }

    public Reply toEntity(ReplyDto replyDto) {
        return modelMapper.map(replyDto, Reply.class);
    }

    public List<ReplyDto> toDtoList(List<Reply> replies) {
        Type listType = new TypeToken<List<ReplyDto>>() {}.getType();
        return modelMapper.map(replies, listType);
    }

    public List<Reply> toEntityList(List<ReplyDto> replyDtos) {
        Type listType = new TypeToken<List<Reply>>() {}.getType();
        return modelMapper.map(replyDtos, listType);
    }
}