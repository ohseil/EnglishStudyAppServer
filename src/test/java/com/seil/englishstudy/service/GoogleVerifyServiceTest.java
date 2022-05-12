package com.seil.englishstudy.service;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.seil.englishstudy.Model.GoogleProfile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GoogleVerifyServiceTest {

    @InjectMocks
    private GoogleVerityService googleVerityService;

    @Mock
    private GoogleVerityService.GoogleIdTokenVerifyService googleIdTokenVerifyService;

    @Test
    void verify테스트() {

        // given
        given(googleIdTokenVerifyService.verify())
                .willReturn(true);
        given(googleIdTokenVerifyService.getEmail())
                .willReturn("test@gmail.com");
        given(googleIdTokenVerifyService.getName())
                .willReturn("test_name");

        // when
        final GoogleProfile googleProfile = googleVerityService.getGoogleProfile("token");

        // then
        assertThat(googleProfile.getEmail())
                .isEqualTo("test@gmail.com");
        assertThat(googleProfile.getName())
                .isEqualTo("test_name");
    }
}
