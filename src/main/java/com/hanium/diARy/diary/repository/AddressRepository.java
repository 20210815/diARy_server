package com.hanium.diARy.diary.repository;


import com.hanium.diARy.diary.dto.DiaryAddressDto;
import com.hanium.diARy.diary.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

@Repository
public class AddressRepository {
    private final AddressRepositoryInterface addressRepositoryInterface;
    private final DiaryLocationInterface diaryLocationInterface;

    public AddressRepository(
            @Autowired AddressRepositoryInterface addressRepositoryInterface,
            @Autowired DiaryLocationInterface diaryLocationInterface
    ) {
        this.addressRepositoryInterface = addressRepositoryInterface;
        this.diaryLocationInterface = diaryLocationInterface;
    }

    public void deleteAddress() {
        Iterator<Address> addressIterator = addressRepositoryInterface.findAll().iterator();
        while(addressIterator.hasNext()) {
            Address address = addressIterator.next();
            if(diaryLocationInterface.findByXAndY(address.getX(), address.getY()) == null) {
                addressRepositoryInterface.delete(address);
            }
        }
    }

}
