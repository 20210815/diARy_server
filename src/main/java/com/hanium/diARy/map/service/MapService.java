package com.hanium.diARy.map.service;


import com.hanium.diARy.diary.dto.DiaryLocationDto;
import com.hanium.diARy.diary.entity.Address;
import com.hanium.diARy.diary.repository.AddressRepositoryInterface;
import com.hanium.diARy.map.dto.MapAllDiaryDto;
import com.hanium.diARy.map.dto.MapDiaryDto;
import com.hanium.diARy.map.repository.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MapService {

    private final MapRepository mapRepository;
    private final AddressRepositoryInterface addressRepositoryInterface;
    public MapService(
            @Autowired MapRepository mapRepository,
            @Autowired AddressRepositoryInterface addressRepositoryInterface
    ) {
        this.mapRepository = mapRepository;
        this.addressRepositoryInterface = addressRepositoryInterface;
    }

    public List<MapDiaryDto> readAllDiaryByAddress(String address) {
        return this.mapRepository.readAllDiaryByAddress(address);
    }

    public List<MapAllDiaryDto> readAllDiary() {
        Iterator<Address> address = addressRepositoryInterface.findAll().iterator();
        List<MapAllDiaryDto> mapAllDiaryDtos = new ArrayList<>();
        while(address.hasNext()) {
            List<MapDiaryDto> mapDiaryDtos = this.mapRepository.readAllDiaryByAddress(address.next().getAddress());
            mapAllDiaryDtos.add(new MapAllDiaryDto(
                    mapDiaryDtos
            ));

        }
        return mapAllDiaryDtos;
    }
}
