package com.seil.englishstudy.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.seil.englishstudy.Model.GoogleProfile;
import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.repository.UserJpaRepository;
import com.seil.englishstudy.service.GoogleVerityService;
import com.seil.englishstudy.web.rest.SignController;
import com.seil.englishstudy.web.rest.config.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

@WebMvcTest(value = SignController.class)
public class SignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserJpaRepository userJpaRepo;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private GoogleVerityService googleVerityService;
    @MockBean
    AuthenticationManager authenticationManager;

    @Test
    void 회원가입() throws Exception {

        // given
        final GoogleProfile googleProfile = GoogleProfile.builder()
                                                .name("test_name")
                                                .email("test@gmail.com")
                                                .build();
        final User user = User.builder()
                            .uid("test@gmail.com")
                            .name("test_name")
                            .password("")
                            .roles(Arrays.asList("ROLE_USER"))
                            .build();

        given(googleVerityService.getGoogleProfile("IdToken"))
                .willReturn(googleProfile);
        given(userJpaRepo.findByUid(googleProfile.getEmail()))
                .willReturn(null);
        given(jwtTokenProvider.createToken(user.getUid(), user.getRoles()))
                .willReturn("jwt");

        // when
        final MvcResult result = mockMvc.perform(
                        post("/signin")
                                .queryParam("accessToken", "IdToken")
                                //.contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("jwt");

    }

    @Test
    void 로그인() throws Exception {

        // given
        final GoogleProfile googleProfile = GoogleProfile.builder()
                                                .name("test_name")
                                                .email("test@gmail.com")
                                                .build();
        final User user = User.builder()
                            .uid("test@gmail.com")
                            .name("test_name")
                            .password("")
                            .roles(Arrays.asList("ROLE_USER"))
                            .build();

        given(googleVerityService.getGoogleProfile("IdToken"))
                .willReturn(googleProfile);
        given(userJpaRepo.findByUid(googleProfile.getEmail()))
                .willReturn(user);
        given(jwtTokenProvider.createToken(user.getUid(), user.getRoles()))
                .willReturn("jwt");

        // when
        final MvcResult result = mockMvc.perform(
                        post("/signin")
                                .queryParam("accessToken", "IdToken")
                                //.contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("jwt");
    }
}
