package com.seil.englishstudy.controller;

import com.seil.englishstudy.service.user.AuthService;
import com.seil.englishstudy.web.rest.UserController;
import com.seil.englishstudy.web.rest.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;
    @MockBean
    private JwtProvider jwtProvider;

    @Test
    void 회원가입or로그인() throws Exception {

        // given
        final String idToken = "idToken";
        final String jwt = "jwt";

        given(authService.signIn("idToken"))
                .willReturn(jwt);

        // when
        final MvcResult result = mockMvc.perform(
                post("/users")
                        .content(idToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(jwt);
    }

    @Test
    void 회원탈퇴() throws Exception {

        // given

        // when
        final MvcResult result = mockMvc.perform(
                        delete("/users")
                                .header("X-AUTH-TOKEN", "jwt")
                                .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();

    }
}
