package com.seil.englishstudy.service;

import com.seil.englishstudy.repository.UserJpaRepository;
import com.seil.englishstudy.web.rest.exception.SigninFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserJpaRepository userJpaRepo;

    public UserDetails loadUserByUsername(String email) {
        return userJpaRepo.findByUid(email);
    }
}