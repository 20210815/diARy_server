package com.hanium.diARy.auth.controller;

import com.hanium.diARy.auth.repository.AuthRepository;
import com.hanium.diARy.auth.service.AuthService;
import com.hanium.diARy.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join")
    public Long join(@RequestBody User user) {
        User userEntity = new User();
        if (authRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setEmail(user.getEmail());
        userEntity.setUsername(user.getUsername());
        authRepository.save(userEntity);
        return userEntity.getUserId();
    }

}
