package com.hanium.diARy.home.hottopic.service;

import com.hanium.diARy.diary.dto.DiaryResponseDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryTag;
import com.hanium.diARy.diary.repository.DiaryRepository;
import com.hanium.diARy.diary.repository.DiaryRepositoryInterface;
import com.hanium.diARy.diary.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final DiaryRepository diaryRepository;
    private final TagRepository tagRepository;
    public TopicService(
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired DiaryRepository diaryRepository,
            @Autowired TagRepository tagRepository
            )
    {
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.diaryRepository = diaryRepository;
        this.tagRepository = tagRepository;
    }

    public List<DiaryResponseDto> readBestDiaryTag() {
        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();
        List<DiaryTag> diaryTags = tagRepository.DescDiaryTag();
        List<Diary> diaries = diaryTags.get(0).getDiaries();
        System.out.println("제일 많은 다이어리 태그" + diaryTags.get(0));
            for(Diary diary : diaries) {
                DiaryResponseDto diaryResponseDto = diaryRepository.readDiary(diary.getDiaryId());
                diaryResponseDtos.add(diaryResponseDto);
            }

//        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();
//        for(Diary diary : diaries) {
//            DiaryResponseDto diaryResponseDto = diaryRepository.readDiary(diary.getDiaryId());
//            diaryResponseDtos.add(diaryResponseDto);
//        }
        return diaryResponseDtos;
    }

//    public List<DiaryResponseDto> readSecondDiaryTag() {
//        List<>
//    }

//    public List<DiaryTag> readBestDiaryTag() {
//        //1번째 tag에 대한 다이어리들을 전달해줘야 함
//        //다이어리의 번호에 따라 diaryRespons
//    }
}
