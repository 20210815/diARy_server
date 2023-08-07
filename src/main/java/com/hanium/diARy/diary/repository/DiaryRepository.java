package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.CommentMapper;
import com.hanium.diARy.diary.dto.CommentDto;
import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.dto.DiaryTagDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryTag;
import com.hanium.diARy.plan.dto.TagDto;
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
    private final CommentMapper commentMapper;

    public DiaryRepository(
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired TagRepositoryInterface tagRepositoryInterface,
            @Autowired CommentMapper commentMapper

            ) {
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.tagRepositoryInterface = tagRepositoryInterface;
        this.commentMapper = commentMapper;
    }

    @Transactional
    public Long createDiary(DiaryDto diaryDto) {
        // Create a new DiaryEntity instance and set its properties from DiaryDto
        Diary diaryEntity = new Diary();
        diaryEntity.setUser(diaryDto.getUser());
        diaryEntity.setContent(diaryDto.getContent());
        diaryEntity.setPublic(diaryDto.isPublic());

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
        diaryEntity.setTitle(diaryDto.getTitle());
        diaryEntity.setComments(this.commentMapper.toEntityList(diaryDto.getComments()));
        diaryEntity.setSatisfaction(diaryDto.getSatisfaction());
        diaryEntity.setTravelStart(diaryDto.getTravelStart());
        diaryEntity.setTravelEnd(diaryDto.getTravelEnd());

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
        diaryEntity.setContent(diaryDto.getContent());


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
        diaryEntity.setTravelStart(diaryDto.getTravelStart());
        diaryEntity.setTravelEnd(diaryDto.getTravelEnd());
        diaryEntity.setComments(this.commentMapper.toEntityList(diaryDto.getComments()));
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