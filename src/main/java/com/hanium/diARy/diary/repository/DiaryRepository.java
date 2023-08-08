package com.hanium.diARy.diary.repository;


import com.hanium.diARy.diary.CommentMapper;
import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.dto.DiaryTagDto;
import com.hanium.diARy.diary.DiaryLocationMapper;
import com.hanium.diARy.diary.dto.*;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryLocation;
import com.hanium.diARy.diary.entity.DiaryTag;
import com.hanium.diARy.plan.dto.LocationDto;
import com.hanium.diARy.plan.dto.TagDto;
import com.hanium.diARy.plan.entity.Location;
import com.hanium.diARy.plan.entity.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class DiaryRepository{
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final TagRepositoryInterface tagRepositoryInterface;
    private final DiaryLocationMapper diaryLocationMapper;
    private final DiaryLocationRepositoryInterface diaryLocationRepositoryInterface;
    private final CommentMapper commentMapper;

    public DiaryRepository(
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired TagRepositoryInterface tagRepositoryInterface,
            @Autowired CommentMapper commentMapper,
            @Autowired DiaryLocationMapper diaryLocationMapper,
            @Autowired DiaryLocationRepositoryInterface diaryLocationRepositoryInterface

    ) {
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.tagRepositoryInterface = tagRepositoryInterface;
        this.commentMapper = commentMapper;
        this.diaryLocationMapper = diaryLocationMapper;
        this.diaryLocationRepositoryInterface = diaryLocationRepositoryInterface;
    }

    @Transactional
    public Long createDiary(DiaryRequestDto diaryDto) {
        DiaryDto diaryInfo = diaryDto.getDiaryDto();

        // Create a new DiaryEntity instance and set its properties from DiaryDto
        Diary diaryEntity = new Diary();
        diaryEntity.setUser(diaryInfo.getUser());
        diaryEntity.setPublic(diaryInfo.isPublic());
        diaryEntity.setTitle(diaryInfo.getTitle());
        diaryEntity.setComments(this.commentMapper.toEntityList(diaryInfo.getComments()));
        diaryEntity.setSatisfaction(diaryInfo.getSatisfaction());
        diaryEntity.setTravelStart(diaryInfo.getTravelStart());
        diaryEntity.setMemo(diaryInfo.getMemo());
        diaryEntity.setTravelDest(diaryInfo.getTravelDest());
        diaryEntity.setTravelEnd(diaryInfo.getTravelEnd());
        // 적은 내용으로 하나하나 저장해야 함
        this.diaryRepositoryInterface.save(diaryEntity);

/*        List<Location> savedLocations = new ArrayList<>();
        for (LocationDto locationDto : locationDtos) {
            Location location = new Location();
            BeanUtils.copyProperties(locationDto, location);
            location.setPlan(savedPlan);
            savedLocations.add(location);
            locationRepository.save(location);
        }*/

        List<DiaryLocationDto> locationDtos = diaryDto.getDiaryLocationDtoList();


        for (DiaryTagDto tagDto : diaryDto.getDiaryDto().getTags()) {
            //받아온 태그를 하나하나 떼어서 원래 있는 건지 비교해봐야 함
            if(tagRepositoryInterface.findByName(tagDto.getName()) == null) {
                //없으면 만들고 / 있으면 원래 거 그대로 선택...
                DiaryTag tag = new DiaryTag();
                tag.setName(tagDto.getName());
                tag.getDiaries().add(diaryEntity);
                tagRepositoryInterface.save(tag);
                System.out.println("" + tag);
                diaryEntity.getTags().add(tag);
            }
            else {
                DiaryTag tag = tagRepositoryInterface.findByName(tagDto.getName());
                tag.getDiaries().add(diaryEntity);
                tagRepositoryInterface.save(tag);
                diaryEntity.getTags().add(tag);
            }
        }

        // Perform validation if needed (e.g., check for required fields in diaryDto)

        // Save the diaryEntity using the repository
        this.diaryRepositoryInterface.save(diaryEntity);
        return diaryEntity.getDiaryId();
    }

    public Diary readDiary(Long id) {
        Optional<Diary> diaryEntity = this.diaryRepositoryInterface.findById(id);
        if(diaryEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return diaryEntity.get();

    }

    public Iterator<Diary> readPublicDiaryAll() {
        return this.diaryRepositoryInterface.findByIsPublicTrue().iterator();
    }

    public Iterator<Diary> readDiaryAll() {
        return this.diaryRepositoryInterface.findAll().iterator();
    }

    @Transactional
    public void updateDiary(Long id, DiaryDto diaryDto) {
        Optional<Diary> targetEntity = this.diaryRepositoryInterface.findById(id);
        if (targetEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Diary diaryEntity = targetEntity.get();
        diaryEntity.setTitle(diaryDto.getTitle() == null ? diaryEntity.getTitle() : diaryDto.getTitle());
        //diaryEntity.setDiaryLocations(this.diaryLocationMapper.mapToEntityList(diaryDto.getDiaryLocationDtos()));


        for (DiaryTag tags : diaryEntity.getTags()) {
            tagRepositoryInterface.findByName(tags.getName()).getDiaries().remove(diaryEntity);
        }
        diaryEntity.getTags().clear();



        for (DiaryTagDto tagDto : diaryDto.getTags()) {
            //받아온 태그를 하나하나 떼어서 원래 있는 건지 비교해봐야 함
            if(tagRepositoryInterface.findByName(tagDto.getName()) == null) {
                    //없으면 만들고 / 있으면 원래 거 그대로 선택...
                    DiaryTag tag = new DiaryTag();
                    tag.setName(tagDto.getName());
                    tag.getDiaries().add(diaryEntity);
                    tagRepositoryInterface.save(tag);
                    diaryEntity.getTags().add(tag);
                }
                else {
                    DiaryTag tag = tagRepositoryInterface.findByName(tagDto.getName());
                    tag.getDiaries().add(diaryEntity);
                    tagRepositoryInterface.save(tag);
                    diaryEntity.getTags().add(tag);
                }
        }

        diaryEntity.setUser(diaryDto.getUser());
        diaryEntity.setPublic(diaryDto.isPublic());
        diaryEntity.setMemo(diaryDto.getMemo());
        diaryEntity.setTravelDest(diaryDto.getTravelDest());
        diaryEntity.setTravelStart(diaryDto.getTravelStart());
        diaryEntity.setTravelEnd(diaryDto.getTravelEnd());
        diaryEntity.setSatisfaction(diaryDto.getSatisfaction() == 0 ? diaryEntity.getSatisfaction() : diaryDto.getSatisfaction());
        this.diaryRepositoryInterface.save(diaryEntity);
    }

    public void deleteDiary(Long id) {
        Optional<Diary> targetEntity = this.diaryRepositoryInterface.findById(id);
        if(targetEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        this.diaryRepositoryInterface.delete(targetEntity.get());
    }


}