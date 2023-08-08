package com.hanium.diARy.diary.service;

import com.hanium.diARy.diary.CommentMapper;
import com.hanium.diARy.diary.dto.*;
import com.hanium.diARy.diary.entity.*;
import com.hanium.diARy.diary.repository.DiaryLocationRepositoryInterface;
import com.hanium.diARy.diary.repository.DiaryRepository;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import org.slf4j.Logger;
import com.hanium.diARy.diary.dto.DiaryLocationDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DiaryService {
    private static final Logger logger = LoggerFactory.getLogger(DiaryService.class);

    private final DiaryRepository diaryRepository;
    private final CommentMapper commentMapper;
    private final DiaryLocationRepositoryInterface diaryLocationRepositoryInterface;

    public DiaryService(
            @Autowired DiaryRepository diaryRepository,
            @Autowired CommentMapper commentMapper,
            @Autowired DiaryLocationRepositoryInterface diaryLocationRepositoryInterface
            ) {
        this.diaryRepository = diaryRepository;
        this.commentMapper = commentMapper;
        this.diaryLocationRepositoryInterface = diaryLocationRepositoryInterface;
    }

    public Long createDiary(DiaryRequestDto diaryDto) {
        return this.diaryRepository.createDiary(diaryDto);
    }

    public DiaryResponseDto readDiary(Long id) {
        DiaryResponseDto diaryResponseDto = new DiaryResponseDto();
        Diary diaryEntity = this.diaryRepository.readDiary(id);
        DiaryDto dto = new DiaryDto();
        dto.setTitle(diaryEntity.getTitle());
        dto.setUser(diaryEntity.getUser());
        dto.setMemo(diaryEntity.getMemo());
        dto.setTravelDest(diaryEntity.getTravelDest());
        dto.setSatisfaction(diaryEntity.getSatisfaction());
        dto.setPublic(diaryEntity.isPublic());
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


        List<DiaryLocationDto> diaryLocationDtoList = new ArrayList<>();
        List<DiaryLocation> diaryLocationList = this.diaryLocationRepositoryInterface.findByDiary_DiaryId(diaryEntity.getDiaryId());
        for(DiaryLocation diaryLocation : diaryLocationList) {
            DiaryLocationDto diaryLocationDto = new DiaryLocationDto();
            diaryLocationDto.setDiaryLocationId(diaryLocation.getDiaryLocationId());
            diaryLocationDto.setDiaryId(diaryLocation.getDiary().getDiaryId());
            diaryLocationDto.setName(diaryLocation.getName());
            diaryLocationDto.setDate(diaryLocation.getDate());
            diaryLocationDto.setAddress(diaryLocation.getAddress());
            diaryLocationDto.setTimeEnd(diaryLocation.getTimeEnd());
            diaryLocationDto.setTimeStart(diaryLocation.getTimeStart());
            diaryLocationDto.setContent(diaryLocation.getContent());
            diaryLocationDtoList.add(diaryLocationDto);
        }
        diaryResponseDto.setDiaryDto(dto);
        diaryResponseDto.setDiaryLocationDtoList(diaryLocationDtoList);
        return diaryResponseDto;
    }

/*    public List<DiaryResponseDto> readDiaryAll() {
        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();



        Iterator<Diary> iterator = this.diaryRepository.readDiaryAll();
        //다이어리 하나에 DiaryLocation 여러개가 - 한 객체
        //List<DiaryDto> diaryDtoList = new ArrayList<>();
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
            dto.setComments(this.commentMapper.toDtoList(diaryEntity.getComments()));
            dto.setTravelStart(diaryEntity.getTravelStart());
            dto.setTravelEnd(diaryEntity.getTravelEnd());
        }
        return diaryDtoList;
    }*/

    public List<DiaryResponseDto> readPublicDiaryAll() {
        return this.diaryRepository.readPublicDiaryAll();
    }

    public void updateDiary(Long id, DiaryRequestDto diaryDto) {
        this.diaryRepository.updateDiary(id, diaryDto);
    }

    public void deleteDiary(Long id) {
        this.diaryRepository.deleteDiary(id);
    }

    public List<DiaryResponseDto> readDiaryAll() {
        List<DiaryResponseDto> list = new ArrayList<>();

        for (DiaryResponseDto diaryResponseDto : list) {
            Iterator<Diary> diaryIterator = this.diaryRepository.readDiaryAll();
            while (diaryIterator.hasNext()) {
                Diary diaryEntity = diaryIterator.next();
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

                List<DiaryLocationDto> diaryLocationDtoList = new ArrayList<>();
                List<DiaryLocation> diaryLocationList = this.diaryLocationRepositoryInterface.findByDiary_DiaryId(diaryEntity.getDiaryId());
                for(DiaryLocation diaryLocation : diaryLocationList) {
                    DiaryLocationDto diaryLocationDto = new DiaryLocationDto();
                    BeanUtils.copyProperties(diaryLocation,diaryLocationDto);
                    diaryLocationDtoList.add(diaryLocationDto);
                }
                diaryResponseDto.setDiaryLocationDtoList(diaryLocationDtoList);
                diaryResponseDto.setDiaryDto(dto);
            }

        }
        return list;
    }
}
