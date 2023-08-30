package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.entity.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepositoryInterface extends CrudRepository<Address, Long> {

    public Address findByAddress(String address);
}
