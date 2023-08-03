package com.hanium.diARy.user.repository;

import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final UserRepositoryInterface userRepositoryInterface;

    public UserRepository(
            @Autowired UserRepositoryInterface userRepositoryInterface
            ){
        this.userRepositoryInterface = userRepositoryInterface;
    }

    public void createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setCreatedAt(userDto.getCreatedAt());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setImage(user.getImage());
    }
}
