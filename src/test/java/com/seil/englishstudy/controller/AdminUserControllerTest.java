package com.seil.englishstudy.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seil.englishstudy.dto.request.AdminUserCreateRequest;
import com.seil.englishstudy.dto.response.UserReadResponse;
import com.seil.englishstudy.service.admin.AdminUserCreateService;
import com.seil.englishstudy.service.admin.AdminUserDeleteService;
import com.seil.englishstudy.service.admin.AdminUserReadService;
import com.seil.englishstudy.web.rest.AdminUserController;
import com.seil.englishstudy.web.rest.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(roles = {"ADMIN"})
@WebMvcTest(controllers = AdminUserController.class)
public class AdminUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminUserCreateService adminUserCreateService;
    @MockBean
    private AdminUserDeleteService adminUserDeleteService;
    @MockBean
    private AdminUserReadService adminUserReadService;

    @MockBean
    private JwtProvider jwtProvider;

    @Test
    void 관리자계정생성() throws Exception {

        // given
        final AdminUserCreateRequest adminUserCreateRequest = AdminUserCreateRequest.builder()
                                                                                    .email("testAdmin@gmail.com")
                                                                                    .name("testAdmin")
                                                                                    .build();

        final Long userId = 1L;

        given(adminUserCreateService.createAdminUser(adminUserCreateRequest))
                .willReturn(userId);

        final String requestBody = new ObjectMapper().writeValueAsString(adminUserCreateRequest);

        // when
        final MvcResult result = mockMvc.perform(
                        post("/admin/users")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-AUTH-TOKEN", "jwt")
                                .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getHeader("Location"))
                .isEqualTo("/admin/users/" + userId);
    }

    @Test
    void 모든유저데이터조회() throws Exception {

        // given
        final List<UserReadResponse> userReadResponseList = new ArrayList<>();

        given(adminUserReadService.readAllUser())
                .willReturn(userReadResponseList);

        // when
        final MvcResult result = mockMvc.perform(
                        get("/admin/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-AUTH-TOKEN", "jwt")
                                .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        final List<UserReadResponse> response = new ObjectMapper()
                                                        .readValue(result.getResponse().getContentAsString(),
                                                                    new TypeReference<List<UserReadResponse>>(){});

        assertThat(response)
                .isEqualTo(userReadResponseList);
    }

    @Test
    void 관리자계정삭제() throws Exception {

        // given
        final String adminEmail = "testAdmin@gmail.com";

        // when
        final MvcResult result = mockMvc.perform(
                        delete("/admin/users/" + adminEmail)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-AUTH-TOKEN", "jwt")
                                .characterEncoding("UTF-8"))

                // then
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();
    }
}
