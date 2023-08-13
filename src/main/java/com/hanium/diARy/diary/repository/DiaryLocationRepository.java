package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.DiaryLocationDto;
import com.hanium.diARy.diary.dto.DiaryLocationImageDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryLocation;
import com.hanium.diARy.diary.entity.DiaryLocationImage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DiaryLocationRepository {
    private final DiaryLocationRepositoryInterface diaryLocationRepositoryInterface;
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final DiaryLocationImageRepository diaryLocationImageRepository;

    public DiaryLocationRepository(
            @Autowired DiaryLocationRepositoryInterface diaryLocationRepositoryInterface,
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired DiaryLocationImageRepository diaryLocationImageRepository
    ) {
        this.diaryLocationRepositoryInterface = diaryLocationRepositoryInterface;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.diaryLocationImageRepository = diaryLocationImageRepository;
    }


    public List<DiaryLocationDto> readPublicDiaryLocation(Long diaryId) {
        List<DiaryLocation> diaryLocationList = this.diaryLocationRepositoryInterface.findByDiary_DiaryId(diaryId);
        List<DiaryLocationDto> diaryLocationDtoList = new ArrayList<>();
        for (DiaryLocation diaryLocation : diaryLocationList) {
            DiaryLocationDto diaryLocationDto = new DiaryLocationDto();
            BeanUtils.copyProperties(diaryLocation, diaryLocationDto);
            diaryLocationDto.setDiaryId(diaryLocation.getDiary().getDiaryId());
            diaryLocationDto.setDiaryLocationImageDtoList(diaryLocationImageRepository.readImage(diaryLocation));
        }
        return Optional.of(diaryLocationDtoList).filter(list -> !list.isEmpty()).orElse(null);
    }


    //찾아서 들어와 해당 diary에 해당하는 LocationDto만
    public void updateDiaryLocation(DiaryLocationDto diaryLocationDto) {
        Optional<DiaryLocation> optionalDiaryLocation = this.diaryLocationRepositoryInterface.findById(diaryLocationDto.getDiaryLocationId());

        if (optionalDiaryLocation.isPresent()) {
            DiaryLocation diaryLocation = optionalDiaryLocation.get();
            BeanUtils.copyProperties(diaryLocationDto, diaryLocation);

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
                    throw new EntityNotFoundException("Diary with ID " + diaryLocationDto.getDiaryId() + " not found");
                }
            }

            diaryLocationImageRepository.updateImage(diaryLocationDto);

            // Save the updated diary location entity
            this.diaryLocationRepositoryInterface.save(diaryLocation);
        } else {
            throw new EntityNotFoundException("DiaryLocation with ID " + " not found");
        }
    }
}
