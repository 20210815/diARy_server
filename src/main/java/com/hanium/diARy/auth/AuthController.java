package com.hanium.diARy.auth;

import com.hanium.diARy.auth.SecurityConfig;
import com.hanium.diARy.auth.dto.LoginRequestDto;
import com.hanium.diARy.auth.repository.AuthRepository;
import com.hanium.diARy.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {
    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecurityConfig securityConfig;


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

/*    @PostMapping("/login")
    public String login() {
        System.out.println("여기");
        return "login";
    }*/

    @GetMapping("")
    public String getCurrentUser() {
        // 현재 로그인한 사용자의 Authentication 객체를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자 정보를 얻습니다.
        String username = authentication.getName();
        // 사용자의 권한 등 다른 정보도 가져올 수 있습니다.
        // List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();

        return "Current user: " + username;
    }


    @GetMapping("/test")
    public String test() {
        return "test.html";
    }
}
