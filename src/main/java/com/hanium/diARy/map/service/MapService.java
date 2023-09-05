package com.hanium.diARy.map.service;


import com.hanium.diARy.diary.dto.DiaryLocationDto;
import com.hanium.diARy.diary.entity.Address;
import com.hanium.diARy.diary.repository.AddressRepositoryInterface;
import com.hanium.diARy.map.dto.MapAllDiaryDto;
import com.hanium.diARy.map.dto.MapDiaryDto;
import com.hanium.diARy.map.repository.MapRepository;
import com.hanium.diARy.user.entity.User;
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

    public List<MapDiaryDto> readAllDiaryByAddress(Address address, User user) {
        return this.mapRepository.readAllDiaryByAddress(address, user);
    }

    public List<MapAllDiaryDto> readAllDiary(User user) {
        Iterator<Address> address = addressRepositoryInterface.findAll().iterator();
        List<MapAllDiaryDto> mapAllDiaryDtos = new ArrayList<>();
        Address add;
        while(address.hasNext()) {
            add = address.next();
            List<MapDiaryDto> mapDiaryDtos = this.mapRepository.readAllDiaryByAddress(add, user);
            mapAllDiaryDtos.add(new MapAllDiaryDto(
                    add.getAddress().toString(),
                    mapDiaryDtos
            ));

        }
        return mapAllDiaryDtos;
    }
}
