package com.seil.englishstudy.service.user;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.repository.UserRepository;
import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.SigninFailedException;
import com.seil.englishstudy.web.rest.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final GoogleAuthVerifyService googleAuthVerifyService;
    private final JwtProvider jwtProvider;

    public String signIn(final String idToken) {

        GoogleIdToken googleIdToken = googleAuthVerifyService.getGoogleIdToken(idToken);

        if (googleAuthVerifyService.verifyIdToken(googleIdToken) == true) {

            User user = userRepository.findByEmail(googleIdToken.getPayload().getEmail());

            if (user == null) {

                user = User.builder()
                        .email(googleIdToken.getPayload().getEmail())
                        .name(googleIdToken.getPayload().get("name").toString())
                        .roleList(new HashSet<>(Arrays.asList("ROLE_USER")))
                        .createdDate(LocalDateTime.now())
                        .build();

                userRepository.save(user);
            }

            return jwtProvider.createJwt(String.valueOf(user.getId()), user.getRoleList());

       }
        else
            throw new SigninFailedException(HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_NOT_VALID, "not valid token.");

    }

    public void withdrawal(final User user) {
        userRepository.delete(user);
    }
}
