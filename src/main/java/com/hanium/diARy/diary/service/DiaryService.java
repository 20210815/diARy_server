package com.hanium.diARy.diary.service;


import com.hanium.diARy.diary.CommentMapper;
import com.hanium.diARy.diary.DiaryLocationMapper;
import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.dto.DiaryRequestDto;
import com.hanium.diARy.diary.dto.DiaryTagDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryLike;
import com.hanium.diARy.diary.entity.DiaryTag;
import com.hanium.diARy.diary.repository.DiaryRepository;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DiaryService {
    private static final Logger logger = LoggerFactory.getLogger(DiaryService.class);

    private final DiaryRepository diaryRepository;
    private final DiaryLocationMapper diaryLocationMapper;
    private final CommentMapper commentMapper;

    public DiaryService(
            @Autowired DiaryRepository diaryRepository,
            @Autowired CommentMapper commentMapper,
            @Autowired DiaryLocationMapper diaryLocationMapper
            ) {
        this.diaryRepository = diaryRepository;
        this.commentMapper = commentMapper;
        this.diaryLocationMapper = diaryLocationMapper;
    }

/*    public Long createDiary(DiaryDto diaryDto) {
        return this.diaryRepository.createDiary(diaryDto);
    }*/

    public Long createDiary(DiaryRequestDto dto) {
        return this.diaryRepository.createDiary(dto);
    }

    public DiaryDto readDiary(Long id) {
        Diary diaryEntity = this.diaryRepository.readDiary(id);
        DiaryDto dto = new DiaryDto();
        dto.setTitle(diaryEntity.getTitle());
        dto.setUser(diaryEntity.getUser());
        dto.setSatisfaction(diaryEntity.getSatisfaction());
        dto.setPublic(diaryEntity.isPublic());
        dto.setMemo(diaryEntity.getMemo());
        dto.setTravelDest(diaryEntity.getTravelDest());
        dto.setComments(this.commentMapper.toDtoList(diaryEntity.getComments()));
        List<DiaryTagDto> tagDtos = new ArrayList<>();
        for (DiaryTag tag : diaryEntity.getTags()) {
            DiaryTagDto tagDto = new DiaryTagDto();
            BeanUtils.copyProperties(tag, tagDto);
            tagDtos.add(tagDto);
        }
        dto.setTags(tagDtos);
        List<User> userList = new ArrayList<>();
        for(DiaryLike diaryLike : diaryEntity.getDiaryLikes()) {
            userList.add(diaryLike.getUser());
        }
        dto.setTravelStart(diaryEntity.getTravelStart());
        dto.setTravelEnd(diaryEntity.getTravelEnd());
        //dto.setDiaryLocationDtos(this.diaryLocationMapper.mapToDtoList(diaryEntity.getDiaryLocations()));
        return dto;
    }

    public List<DiaryDto> readDiaryAll() {
        Iterator<Diary> iterator = this.diaryRepository.readDiaryAll();
        List<DiaryDto> diaryDtoList = new ArrayList<>();
        while(iterator.hasNext()) {
            Diary diaryEntity = iterator.next();
            DiaryDto dto = new DiaryDto();
            dto.setTitle(diaryEntity.getTitle());
            dto.setUser(diaryEntity.getUser());
            dto.setSatisfaction(diaryEntity.getSatisfaction());
            dto.setPublic(diaryEntity.isPublic());
            dto.setMemo(diaryEntity.getMemo());
            dto.setTravelDest(diaryEntity.getTravelDest());
            List<DiaryTagDto> tagDtos = new ArrayList<>();
            for (DiaryTag tag : diaryEntity.getTags()) {
                DiaryTagDto tagDto = new DiaryTagDto();
                BeanUtils.copyProperties(tag, tagDto);
                tagDtos.add(tagDto);
            }
            dto.setTags(tagDtos);
            List<User> userList = new ArrayList<>();
            for(DiaryLike diaryLike : diaryEntity.getDiaryLikes()) {
                userList.add(diaryLike.getUser());
            }
            dto.setTravelStart(diaryEntity.getTravelStart());
            dto.setTravelEnd(diaryEntity.getTravelEnd());
           // dto.setDiaryLocationDtos(this.diaryLocationMapper.mapToDtoList(diaryEntity.getDiaryLocations()));
            diaryDtoList.add(dto);
        }
        return diaryDtoList;
    }

    public List<DiaryDto> readPublicDiaryAll() {
        Iterator<Diary> iterator = this.diaryRepository.readPublicDiaryAll();
        List<DiaryDto> diaryDtoList = new ArrayList<>();
        while(iterator.hasNext()) {
            Diary diaryEntity = iterator.next();
            DiaryDto dto = new DiaryDto();
            dto.setTitle(diaryEntity.getTitle());
            dto.setUser(diaryEntity.getUser());
            dto.setSatisfaction(diaryEntity.getSatisfaction());
            dto.setPublic(diaryEntity.isPublic());
            List<DiaryTagDto> tagDtos = new ArrayList<>();
            for (DiaryTag tag : diaryEntity.getTags()) {
                DiaryTagDto tagDto = new DiaryTagDto();
                BeanUtils.copyProperties(tag, tagDto);
                tagDtos.add(tagDto);
            }
            dto.setTags(tagDtos);
            List<User> userList = new ArrayList<>();
            for(DiaryLike diaryLike : diaryEntity.getDiaryLikes()) {
                userList.add(diaryLike.getUser());
            }
            dto.setTravelStart(diaryEntity.getTravelStart());
            dto.setTravelEnd(diaryEntity.getTravelEnd());
            //dto.setDiaryLocationDtos(this.diaryLocationMapper.mapToDtoList(diaryEntity.getDiaryLocations()));
            diaryDtoList.add(dto);
        }
        return diaryDtoList;
    }

    public void updateDiary(Long id, DiaryDto diaryDto) {
        this.diaryRepository.updateDiary(id, diaryDto);
    }

    public void deleteDiary(Long id) {
        this.diaryRepository.deleteDiary(id);
    }
}
