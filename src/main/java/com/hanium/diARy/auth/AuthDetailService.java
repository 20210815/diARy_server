package com.hanium.diARy.auth;

import com.hanium.diARy.auth.repository.AuthRepository;
import com.hanium.diARy.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthDetailService implements UserDetailsService {
    private final AuthRepository authRepository;
    @Override
    public AuthDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(email);
        User userEntity = authRepository.findByEmail(email);
        return new AuthDetails(userEntity);
    }


}
