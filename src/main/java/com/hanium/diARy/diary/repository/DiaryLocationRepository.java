package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.AddressDto;
import com.hanium.diARy.diary.dto.DiaryAddressDto;
import com.hanium.diARy.diary.dto.DiaryLocationDto;
import com.hanium.diARy.diary.dto.DiaryLocationImageDto;
import com.hanium.diARy.diary.entity.Address;
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

import static javax.management.Query.and;

@Repository
public class DiaryLocationRepository {
    private final DiaryLocationInterface diaryLocationRepositoryInterface;
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final DiaryLocationImageRepository diaryLocationImageRepository;
    private final AddressRepositoryInterface addressRepositoryInterface;

    public DiaryLocationRepository(
            @Autowired DiaryLocationInterface diaryLocationRepositoryInterface,
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired DiaryLocationImageRepository diaryLocationImageRepository,
            @Autowired AddressRepositoryInterface addressRepositoryInterface
    ) {
        this.diaryLocationRepositoryInterface = diaryLocationRepositoryInterface;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.diaryLocationImageRepository = diaryLocationImageRepository;
        this.addressRepositoryInterface = addressRepositoryInterface;
    }


    public List<DiaryLocationDto> readPublicDiaryLocation(Long diaryId) {
        List<DiaryLocation> diaryLocationList = this.diaryLocationRepositoryInterface.findByDiary_DiaryId(diaryId);
        List<DiaryLocationDto> diaryLocationDtoList = new ArrayList<>();
        for (DiaryLocation diaryLocation : diaryLocationList) {
            DiaryLocationDto diaryLocationDto = new DiaryLocationDto();
            diaryLocationDto.setDiaryLocationId(diaryLocation.getDiaryLocationId());
            diaryLocationDto.setDiaryId(diaryLocation.getDiary().getDiaryId());
            diaryLocationDto.setName(diaryLocation.getName());
            diaryLocationDto.setDate(diaryLocation.getDate());
            //address 자리
            diaryLocationDto.setAddress(diaryLocation.getAddress());
            diaryLocationDto.setX(diaryLocation.getX());
            diaryLocationDto.setY(diaryLocation.getY());

            diaryLocationDto.setTimeEnd(diaryLocation.getTimeEnd());
            diaryLocationDto.setTimeStart(diaryLocation.getTimeStart());
            diaryLocationDto.setContent(diaryLocation.getContent());
            diaryLocationDto.setDiaryLocationImageDtoList(diaryLocationImageRepository.readImage(diaryLocation));
            diaryLocationDtoList.add(diaryLocationDto);
        }
        return Optional.of(diaryLocationDtoList).filter(list -> !list.isEmpty()).orElse(null);
    }

//
//    //찾아서 들어와 해당 diary에 해당하는 LocationDto만
//    public void updateDiaryLocation(DiaryLocationDto diaryLocationDto) {
//        Optional<DiaryLocation> optionalDiaryLocation = this.diaryLocationRepositoryInterface.findById(diaryLocationDto.getDiaryLocationId());
//
//        if (optionalDiaryLocation.isPresent()) {
//            DiaryLocation diaryLocation = optionalDiaryLocation.get();
//            //BeanUtils.copyProperties(diaryLocationDto, diaryLocation);
//
//            //address 있는지 없는지 확인하고 없으면 만들기
//            if (addressRepositoryInterface.existsByXAndY(diaryLocationDto.getX(), diaryLocationDto.getY())) {
//                Address address = addressRepositoryInterface.findByXAndY(diaryLocationDto.getX(), diaryLocationDto.getY());
//                //있다면 해당 address의 정보들을 가져옴
//                diaryLocation.setAddress(address.getAddress());
//                diaryLocation.setX(address.getX());
//                diaryLocation.setY(address.getY());
//                //diaryLocation.setAddress(diaryLocationDto.getAddress());
//
////                if (addressRepositoryInterface.findByAddress(diaryLocation.getAddress()) == null) {
////                    Address address = new Address();
////                    address.setAddress(diaryLocation.getAddress());
////                    addressRepositoryInterface.save(address);
////                }
//            }
//            else {
//                Address address = new Address();
//                address.setAddress(diaryLocationDto.getAddress());
//                address.setX(diaryLocationDto.getX());
//                address.setY(diaryLocationDto.getY());
//                addressRepositoryInterface.save(address);
//            }
//
//            if (diaryLocationDto.getName() != null) {
//                diaryLocation.setName(diaryLocationDto.getName());
//            }
//
//            if (diaryLocationDto.getDate() != null) {
//                diaryLocation.setDate(diaryLocationDto.getDate());
//            }
//
//            if (diaryLocationDto.getTimeStart() != null) {
//                diaryLocation.setTimeStart(diaryLocationDto.getTimeStart());
//            }
//
//            if (diaryLocationDto.getTimeEnd() != null) {
//                diaryLocation.setTimeEnd(diaryLocationDto.getTimeEnd());
//            }
//
//            if (diaryLocationDto.getContent() != null) {
//                diaryLocation.setContent(diaryLocationDto.getContent());
//            }
//
//            if (diaryLocationDto.getDiaryId() != null) {
//                Optional<Diary> optionalDiary = this.diaryRepositoryInterface.findById(diaryLocationDto.getDiaryId());
//                if (optionalDiary.isPresent()) {
//                    diaryLocation.setDiary(optionalDiary.get());
//                } else {
//                    throw new EntityNotFoundException("Diary with ID " + diaryLocationDto.getDiaryId() + " not found");
//                }
//            }
//
//            if(diaryLocationDto.getDiaryLocationImageDtoList() != null) {
//                diaryLocationImageRepository.updateImage(diaryLocationDto);
//            }
//
//
//            // Save the updated diary location entity
//            this.diaryLocationRepositoryInterface.save(diaryLocation);
//        } else {
//            throw new EntityNotFoundException("DiaryLocation with ID " + " not found");
//        }
//    }
}
