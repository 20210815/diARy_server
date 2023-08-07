package com.hanium.diARy.user.repository;

import com.hanium.diARy.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepositoryInterface extends CrudRepository<User, Long> {
    Optional<User> findById(Long id);
}
