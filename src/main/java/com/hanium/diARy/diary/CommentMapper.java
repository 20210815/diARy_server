package com.hanium.diARy.diary;

import com.hanium.diARy.diary.dto.CommentDto;
import com.hanium.diARy.diary.entity.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    private final ModelMapper modelMapper;

    public CommentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CommentDto toDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    public Comment toEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }

    public List<CommentDto> toDtoList(List<Comment> comments) {
        return comments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Comment> toEntityList(List<CommentDto> commentDtos) {
        if (commentDtos == null) {
            return new ArrayList<>(); // 또는 null 처리에 따라 적절한 값 반환
        }
        return commentDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}