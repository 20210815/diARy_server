package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.entity.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepositoryInterface extends CrudRepository<Address, Long> {
    //public Address findByAddress(String address);
    public Address findByAddress(String address);
    public Boolean existsByXAndY(String x, String y);
    public Address findByXAndY(String x, String y);
}
