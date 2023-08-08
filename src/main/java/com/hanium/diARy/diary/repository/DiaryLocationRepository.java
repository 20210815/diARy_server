package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.DiaryLocationDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryLocation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DiaryLocationRepository {
    private final DiaryLocationRepositoryInterface diaryLocationRepositoryInterface;
    private final DiaryRepositoryInterface diaryRepositoryInterface;

    public DiaryLocationRepository(
            @Autowired DiaryLocationRepositoryInterface diaryLocationRepositoryInterface,
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface
    ) {
        this.diaryLocationRepositoryInterface = diaryLocationRepositoryInterface;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
    }


    public List<DiaryLocationDto> readPublicDiaryLocation(Long diaryId) {
        List<DiaryLocation> diaryLocationList = this.diaryLocationRepositoryInterface.findByDiary_DiaryId(diaryId);
        List<DiaryLocationDto> diaryLocationDtoList = new ArrayList<>();
        for (DiaryLocation diaryLocation : diaryLocationList) {
            DiaryLocationDto diaryLocationDto = new DiaryLocationDto();
            BeanUtils.copyProperties(diaryLocation, diaryLocationDto);
            diaryLocationDto.setDiaryId(diaryLocation.getDiary().getDiaryId());
            diaryLocationDtoList.add(diaryLocationDto);
        }
        return Optional.of(diaryLocationDtoList).filter(list -> !list.isEmpty()).orElse(null);
    }


    //찾아서 들어와 해당 diary에 해당하는 LocationDto만
    public void updateDiaryLocation(DiaryLocationDto diaryLocationDto, Long id) {
        Optional<DiaryLocation> optionalDiaryLocation = this.diaryLocationRepositoryInterface.findById(id);

        if (optionalDiaryLocation.isPresent()) {
            DiaryLocation diaryLocation = optionalDiaryLocation.get();

            if (diaryLocationDto.getAddress() != null) {
                diaryLocation.setAddress(diaryLocationDto.getAddress());
            }

            if (diaryLocationDto.getName() != null) {
                diaryLocation.setName(diaryLocationDto.getName());
            }

            if (diaryLocationDto.getDate() != null) {
                diaryLocation.setDate(diaryLocationDto.getDate());
            }

            if (diaryLocationDto.getTimeStart() != null) {
                diaryLocation.setTimeStart(diaryLocationDto.getTimeStart());
            }

            if (diaryLocationDto.getTimeEnd() != null) {
                diaryLocation.setTimeEnd(diaryLocationDto.getTimeEnd());
            }

            if (diaryLocationDto.getContent() != null) {
                diaryLocation.setContent(diaryLocationDto.getContent());
            }

            if (diaryLocationDto.getDiaryId() != null) {
                Optional<Diary> optionalDiary = this.diaryRepositoryInterface.findById(diaryLocationDto.getDiaryId());
                if (optionalDiary.isPresent()) {
                    diaryLocation.setDiary(optionalDiary.get());
                } else {
                    // 처리할 예외 또는 오류 상황을 정의
                    throw new EntityNotFoundException("Diary with ID " + diaryLocationDto.getDiaryId() + " not found");
                }
            }

            this.diaryLocationRepositoryInterface.save(diaryLocation);
        } else {
            // 처리할 예외 또는 오류 상황을 정의
            throw new EntityNotFoundException("DiaryLocation with ID " + id + " not found");
        }
    }
}
