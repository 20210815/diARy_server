package com.hanium.diARy.home.hottopic.service;

import com.hanium.diARy.diary.dto.DiaryResponseDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryTag;
import com.hanium.diARy.diary.repository.DiaryRepository;
import com.hanium.diARy.diary.repository.DiaryRepositoryInterface;
import com.hanium.diARy.diary.repository.TagRepository;
import com.hanium.diARy.home.hottopic.dto.HottopicDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<HottopicDto> readBestDiaryTag() {
        //3순위까지만
        List<DiaryTag> diaryTags;
        if (tagRepository.DescDiaryTag().size() <= 3) {
            diaryTags = tagRepository.DescDiaryTag();
        }
        else {
            diaryTags = tagRepository.DescDiaryTag().subList(0,3);
        }
        List<HottopicDto> hottopicDtos = new ArrayList<>();

        for (DiaryTag diaryTag : diaryTags) {
            HottopicDto hottopicDto = new HottopicDto();
            List<Diary> diaries = diaryTag.getDiaries();
            List<Diary> sortedDiaries = diaries.stream()
                    .sorted(Comparator.comparingInt(Diary::getLikesCount).reversed())
                    .collect(Collectors.toList());
            // 공개된 것만 고르기

            List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>(); // 이 부분을 수정

            for (Diary diary : sortedDiaries) {
                if(diaryResponseDtos.size() < 4) {
                    if (diary.isPublic() == true) {
                        DiaryResponseDto diaryResponseDto = diaryRepository.readDiary(diary.getDiaryId());
                        diaryResponseDtos.add(diaryResponseDto);
                    }
                }
                else break;
            }

            hottopicDto.setTagname(diaryTag.getName());
            hottopicDto.setDiaryResponseDtoList(diaryResponseDtos);
            hottopicDtos.add(hottopicDto);
        }

        return hottopicDtos;
    }


//    public List<DiaryResponseDto> readSecondDiaryTag() {
//        List<>
//    }

//    public List<DiaryTag> readBestDiaryTag() {
//        //1번째 tag에 대한 다이어리들을 전달해줘야 함
//        //다이어리의 번호에 따라 diaryRespons
//    }
}
