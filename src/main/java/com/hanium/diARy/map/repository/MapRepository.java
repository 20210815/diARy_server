package com.hanium.diARy.map.repository;


import com.hanium.diARy.diary.dto.DiaryLocationDto;
import com.hanium.diARy.diary.entity.DiaryLocation;
import com.hanium.diARy.diary.repository.DiaryLocationImageRepository;
import com.hanium.diARy.diary.repository.DiaryLocationInterface;
import com.hanium.diARy.diary.repository.DiaryLocationRepository;
import com.hanium.diARy.map.dto.MapDiaryDto;
import com.hanium.diARy.user.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class MapRepository {

    private final DiaryLocationInterface diaryLocationRepositoryInterface;
    private final DiaryLocationImageRepository diaryLocationImageRepository;
    public MapRepository(
            @Autowired DiaryLocationInterface diaryLocationRepositoryInterface,
            @Autowired DiaryLocationImageRepository diaryLocationImageRepository
            ) {
        this.diaryLocationRepositoryInterface = diaryLocationRepositoryInterface;
        this.diaryLocationImageRepository = diaryLocationImageRepository;
    }


    public List<MapDiaryDto> readAllDiaryByAddress(String address) {
        List<MapDiaryDto> mapDiaryDtos = new ArrayList<>();
        List<DiaryLocation> diaryLocations = diaryLocationRepositoryInterface.findByAddress(address);
        for(DiaryLocation diaryLocation : diaryLocations) {
            System.out.println(diaryLocation.getDiary().getDiaryId());
            if(diaryLocation.getDiary().isPublic()){
                MapDiaryDto mapDiaryDto = new MapDiaryDto();
                DiaryLocationDto diaryLocationDto = new DiaryLocationDto();
                BeanUtils.copyProperties(diaryLocation, diaryLocationDto);
                diaryLocationDto.setDiaryId(diaryLocation.getDiary().getDiaryId());
                diaryLocationDto.setDiaryLocationImageDtoList(diaryLocationImageRepository.readImage(diaryLocation));
                mapDiaryDto.setDiaryLocationDto(diaryLocationDto);
                mapDiaryDto.setDiaryId(diaryLocation.getDiary().getDiaryId());
                mapDiaryDto.setSatisfaction(diaryLocation.getDiary().getSatisfaction());
                UserDto userDto = new UserDto();
                BeanUtils.copyProperties(diaryLocation.getDiary().getUser(), userDto);
                mapDiaryDto.setUserDto(userDto);
                mapDiaryDto.setTravelStart(diaryLocation.getDiary().getTravelStart());
                mapDiaryDto.setTravelEnd(diaryLocation.getDiary().getTravelEnd());
                mapDiaryDtos.add(mapDiaryDto);
            }
        }
        return mapDiaryDtos;
    }

}
