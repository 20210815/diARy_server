package com.hanium.diARy.diary.repository;


import com.hanium.diARy.diary.dto.DiaryAddressDto;
import com.hanium.diARy.diary.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepository {
    private final AddressRepositoryInterface addressRepositoryInterface;

    public AddressRepository(
            @Autowired AddressRepositoryInterface addressRepositoryInterface
    ) {
        this.addressRepositoryInterface = addressRepositoryInterface;
    }

}
