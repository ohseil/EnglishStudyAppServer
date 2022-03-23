package com.seil.englishstudy.web.rest;

import com.seil.englishstudy.Model.GoogleProfile;
import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.repository.UserJpaRepository;
import com.seil.englishstudy.service.GoogleVerityService;
import com.seil.englishstudy.web.rest.config.security.JwtTokenProvider;
import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.SigninFailedException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;

@Api(tags = {"Sign"})
@RequiredArgsConstructor
@RestController
public class SignController {

    private final UserJpaRepository userJpaRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final GoogleVerityService googleVerityService;

    private static final String adminEmail = "siohbaram@gmail.com";

    @ApiOperation(value = "Sign in or Sign up", notes = "google access token을 사용해 로그인 또는 회원가입 후 JWT 발급")
    @PostMapping(value = "/signin")
    public ResponseEntity signin(@ApiParam(value = "accessToken", required = true) @RequestParam String accessToken) {

        GoogleProfile gp = googleVerityService.getGoogleProfile(accessToken);

        User user = userJpaRepo.findByUid(gp.getEmail());

        if (user == null) {
            // sign up.
            if (gp.getEmail().equals(adminEmail)) {
                // registered admin email
                user = User.builder()
                        .uid(gp.getEmail())
                        .name(gp.getName())
                        .password("")
                        .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                        .build();
            }
            else {
                user = User.builder()
                        .uid(gp.getEmail())
                        .name(gp.getName())
                        .password("")
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build();
            }
            userJpaRepo.saveAndFlush(user);
        }

        return ResponseEntity.ok(jwtTokenProvider.createToken(user.getUid(), user.getRoles()));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "access_token after sign in", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "Get User data.", notes = "사용자 정보 조회.")
    @GetMapping(value = "/users")
    public ResponseEntity getUser(@ApiParam(value = "email", required = true) @RequestParam String email) {

        User user = userJpaRepo.findByUid(email);

        if (user == null)
            throw new SigninFailedException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "not exist user data.");

        return ResponseEntity.ok(user);
    }
}
