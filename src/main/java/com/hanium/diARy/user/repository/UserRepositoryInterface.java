package com.hanium.diARy.user.repository;

import com.hanium.diARy.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepositoryInterface extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    User findByUsername(String username);
    User findByEmail(String email);
}
