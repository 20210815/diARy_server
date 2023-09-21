package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class TagRepository {
    private final TagRepositoryInterface tagRepositoryInterface;
    private final DiaryRepositoryInterface diaryRepositoryInterface;

    public TagRepository(
            @Autowired TagRepositoryInterface tagRepositoryInterface,
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface
            ){
        this.tagRepositoryInterface = tagRepositoryInterface;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
    }
    public DiaryTag findBestTag() {
        // 다이어리 태그의 다이어리스 개수를 알아야 함

        // 온 다이어리 태그를 불러옴
        // 다이어리스의 count를 중에서 제일 큰 걸 찾아야 함
        Iterator<DiaryTag> diaryTagIterator = allTagList().iterator();
        DiaryTag bestTag = null;
        int maxNumber = 0; // 가장 큰 개수를 추적하는 변수를 추가하고 0으로 초기화합니다.

        while (diaryTagIterator.hasNext()) {
            DiaryTag diaryTag = diaryTagIterator.next();
            int currentDiaryCount = diaryTag.getDiaries().size(); // 현재 다이어리 개수를 가져옵니다.

            // 현재 다이어리 개수가 최대 개수보다 크면 최대 개수를 업데이트하고 bestTag를 설정합니다.
            if (currentDiaryCount > maxNumber) {
                maxNumber = currentDiaryCount;
                bestTag = diaryTag;
            }
        }

        if (bestTag != null) {
            System.out.println("Best Tag Name: " + bestTag.getName());
        } else {
            System.out.println("No Best Tag Found");
        }

        return bestTag;
    }

    //정렬
    public List<DiaryTag> DescDiaryTag() {
//        Iterator<DiaryTag> diaryTagIterator = tagRepositoryInterface.findAll().iterator();

//        while (diaryTagIterator.hasNext()) {
//            DiaryTag diaryTag = diaryTagIterator.next();
//            List<Diary> diaries = diaryRepositoryInterface.findByTags(diaryTag);
//
//            // 해당 태그와 관련된 모든 Diary를 가져옴
//            List<Diary> tagDiary = diaryTag.getDiaries();
//
//            // diaries에는 포함되지만 tagDiary에는 포함되지 않은 Diary를 찾아서 추가-
//            for (Diary diary : diaries) {
//                if (!tagDiary.contains(diary)) {
//                    diaryTag.getDiaries().add(diary);
//                }
//            }
//            diaryTag.setNumber(diaryTag.getDiaries().size());
//            tagRepositoryInterface.save(diaryTag);
//        }

        List<DiaryTag> diaryTags = tagRepositoryInterface.findAllByOrderByNumberDesc();
        if (diaryTags.size() >= 3) {
            diaryTags = diaryTags.subList(0,3);
        }

        System.out.println(diaryTags);
        return diaryTags;
    }

    //태그를 통해서 다이어리 반환
    //public List<DiaryTag>


    public Iterable<DiaryTag> allTagList() {
        return tagRepositoryInterface.findAll();
    }
}
