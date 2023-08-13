package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.entity.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepositoryInterface extends CrudRepository<Address, Long> {

    public Address findByAddress(String address);
}
