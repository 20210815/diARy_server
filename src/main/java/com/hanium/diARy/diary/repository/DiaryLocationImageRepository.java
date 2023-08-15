package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.DiaryLocationDto;
import com.hanium.diARy.diary.dto.DiaryLocationImageDto;
import com.hanium.diARy.diary.entity.DiaryLocation;
import com.hanium.diARy.diary.entity.DiaryLocationImage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DiaryLocationImageRepository {
    private final DiaryLocationImageRepositoryInterface diaryLocationImageRepositoryInterface;
    private final DiaryLocationInterface diaryLocationRepositoryInterface;

    public DiaryLocationImageRepository(
            @Autowired DiaryLocationImageRepositoryInterface diaryLocationImageRepositoryInterface,
            @Autowired DiaryLocationInterface diaryLocationRepositoryInterface
    ) {
        this.diaryLocationImageRepositoryInterface = diaryLocationImageRepositoryInterface;
        this.diaryLocationRepositoryInterface = diaryLocationRepositoryInterface;
    }

    public void createImage(DiaryLocationImageDto diaryLocationImageDto, Long locationId) {
        DiaryLocationImage diaryLocationImage = new DiaryLocationImage();
        DiaryLocation diaryLocation = diaryLocationRepositoryInterface.findById(locationId).get();
        diaryLocationImage.setDiaryLocation(diaryLocation);
        diaryLocationImage.setImageData(diaryLocationImageDto.getImageData());
        diaryLocationImageRepositoryInterface.save(diaryLocationImage);
    }


    public List<DiaryLocationImageDto> readImage(DiaryLocation diaryLocation) {
        List<DiaryLocationImageDto> diaryLocationImageDtoList = new ArrayList<>();
        List<DiaryLocationImage> diaryLocationImages = diaryLocationImageRepositoryInterface.findByDiaryLocation(diaryLocation);
        for(DiaryLocationImage diaryLocationImage : diaryLocationImages) {
            DiaryLocationImageDto diaryLocationImageDto = new DiaryLocationImageDto();
            BeanUtils.copyProperties(diaryLocationImage, diaryLocationImageDto);
            diaryLocationImageDto.setDiaryLocationId(diaryLocation.getDiaryLocationId());
            diaryLocationImageDtoList.add(diaryLocationImageDto);
        }

        return diaryLocationImageDtoList;
    }

    public void updateImage(DiaryLocationDto diaryLocationDto) {
        DiaryLocation diaryLocation = diaryLocationRepositoryInterface.findById(diaryLocationDto.getDiaryLocationId()).get();
        List<DiaryLocationImageDto> diaryLocationImageDtoList = diaryLocationDto.getDiaryLocationImageDtoList();
        List<DiaryLocationImage> diaryLocationImages = diaryLocation.getImages();

        // Clear the existing images and update the list with new images
        diaryLocationImages.clear();

        for (DiaryLocationImageDto diaryLocationImageDto : diaryLocationImageDtoList) {
            DiaryLocationImage diaryLocationImage = new DiaryLocationImage();
            BeanUtils.copyProperties(diaryLocationImageDto, diaryLocationImage);
            diaryLocationImage.setDiaryLocation(diaryLocation);
            diaryLocationImages.add(diaryLocationImage);
        }

        diaryLocationRepositoryInterface.save(diaryLocation);
    }



}
