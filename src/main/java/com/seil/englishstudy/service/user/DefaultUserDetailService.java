package com.seil.englishstudy.service.user;

import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.repository.UserRepository;
import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.SigninFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DefaultUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String userId) {

        Optional<User> user = userRepository.findById(Long.parseLong(userId));

        user.orElseThrow(() -> new SigninFailedException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_DATA, "no user."));

        return user.get();
    }
}
