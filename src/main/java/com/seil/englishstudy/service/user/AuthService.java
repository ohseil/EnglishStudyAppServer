package com.seil.englishstudy.service.user;

import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.repository.UserRepository;
import com.seil.englishstudy.vo.GoogleProfile;
import com.seil.englishstudy.web.rest.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final GoogleVerifyService googleVerifyService;
    private final JwtProvider jwtProvider;

    public String signIn(final String idToken) {

        final GoogleProfile googleProfile = googleVerifyService.verify(idToken);

        User user = userRepository.findByEmail(googleProfile.getEmail());

        if (user == null) {

            user = User.builder()
                    .email(googleProfile.getEmail())
                    .name(googleProfile.getName())
                    .roleList(new HashSet<>(Arrays.asList("ROLE_USER")))
                    .createdDate(LocalDateTime.now())
                    .build();

            userRepository.save(user);
        }

        return jwtProvider.createJwt(String.valueOf(user.getId()), user.getRoleList());

    }

    public void withdrawal(final User user) {
        userRepository.delete(user);
    }
}
