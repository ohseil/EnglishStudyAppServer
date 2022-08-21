package com.seil.englishstudy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seil.englishstudy.dto.request.AdminEngStudyCreateRequest;
import com.seil.englishstudy.dto.request.AdminEngStudyUpdateRequest;
import com.seil.englishstudy.dto.response.AdminEngStudyReadResponse;
import com.seil.englishstudy.dto.response.AdminEngStudyUpdateResponse;
import com.seil.englishstudy.service.admin.AdminEngStudyCreateService;
import com.seil.englishstudy.service.admin.AdminEngStudyDeleteService;
import com.seil.englishstudy.service.admin.AdminEngStudyReadService;
import com.seil.englishstudy.service.admin.AdminEngStudyUpdateService;
import com.seil.englishstudy.web.rest.AdminEngStudyController;
import com.seil.englishstudy.web.rest.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(roles = {"ADMIN"})
@WebMvcTest(AdminEngStudyController.class)
public class AdminEngStudyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminEngStudyCreateService adminEngStudyCreateService;
    @MockBean
    private AdminEngStudyReadService adminEngStudyReadService;
    @MockBean
    private AdminEngStudyUpdateService adminEngStudyUpdateService;
    @MockBean
    private AdminEngStudyDeleteService adminEngStudyDeleteService;

    @MockBean
    private JwtProvider jwtProvider;

    @Test
    void 영어공부데이터생성() throws Exception {

        // given
        final AdminEngStudyCreateRequest adminEngStudyCreateRequest = AdminEngStudyCreateRequest.builder()
                                                                                                .categoryCode(1)
                                                                                                .question("question")
                                                                                                .answer("answer")
                                                                                                .build();

        final Long engStudyDataId = 1L;

        given(adminEngStudyCreateService.createEngStudyData(adminEngStudyCreateRequest))
                .willReturn(engStudyDataId);

        final String requestBody = new ObjectMapper().writeValueAsString(adminEngStudyCreateRequest);

        // when
        final MvcResult result = mockMvc.perform(
                        post("/admin/studydatas")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-AUTH-TOKEN", "jwt")
                                .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getHeader("Location"))
                .isEqualTo("/admin/studydatas/" + engStudyDataId);
    }

    @Test
    void 영어공부데이터id로조회() throws Exception {

        // given
        final Long engStudyDataId = 1L;
        final AdminEngStudyReadResponse adminEngStudyReadResponse = AdminEngStudyReadResponse.builder()
                                                                                            .categoryCode(1)
                                                                                            .question("question")
                                                                                            .answer("answer")
                                                                                            .build();

        final String responseJson = new ObjectMapper().writeValueAsString(adminEngStudyReadResponse);

        given(adminEngStudyReadService.readEngStudyById(engStudyDataId))
                .willReturn(adminEngStudyReadResponse);

        // when
        final MvcResult result = mockMvc.perform(
                        get("/admin/studydatas/" + engStudyDataId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-AUTH-TOKEN", "jwt")
                                .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(responseJson);
    }

    @Test
    void 영어공부데이터변경내용없는수정() throws Exception {

        // given
        final Long engStudyDataId = 1L;
        final AdminEngStudyUpdateRequest adminEngStudyUpdateRequest = AdminEngStudyUpdateRequest.builder()
                                                                                                .categoryCode(1)
                                                                                                .question("question")
                                                                                                .answer("answer")
                                                                                                .build();

        final AdminEngStudyUpdateResponse adminEngStudyUpdateResponse = AdminEngStudyUpdateResponse.builder()
                                                                                                    .categoryCode(1)
                                                                                                    .question("question")
                                                                                                    .answer("answer")
                                                                                                    .build();

        given(adminEngStudyUpdateService.updateEngStudyData(engStudyDataId, adminEngStudyUpdateRequest))
                .willReturn(adminEngStudyUpdateResponse);

        final String requestBody = new ObjectMapper().writeValueAsString(adminEngStudyUpdateRequest);

        // when
        final MvcResult result = mockMvc.perform(
                        put("/admin/studydatas/" + engStudyDataId)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-AUTH-TOKEN", "jwt")
                                .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();
    }

    @Test
    void 영어공부데이터변경내용있는수정() throws Exception {

        // given
        final Long engStudyDataId = 1L;
        final AdminEngStudyUpdateRequest adminEngStudyUpdateRequest = AdminEngStudyUpdateRequest.builder()
                                                                                                .categoryCode(1)
                                                                                                .question("updated question")
                                                                                                .answer("updated answer")
                                                                                                .build();

        final AdminEngStudyUpdateResponse adminEngStudyUpdateResponse = AdminEngStudyUpdateResponse.builder()
                                                                                                    .categoryCode(1)
                                                                                                    .question("question")
                                                                                                    .answer("answer")
                                                                                                    .build();

        given(adminEngStudyUpdateService.updateEngStudyData(engStudyDataId, adminEngStudyUpdateRequest))
                .willReturn(adminEngStudyUpdateResponse);

        final String requestBody = new ObjectMapper().writeValueAsString(adminEngStudyUpdateRequest);

        // when
        final MvcResult result = mockMvc.perform(
                        put("/admin/studydatas/" + engStudyDataId)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-AUTH-TOKEN", "jwt")
                                .characterEncoding("UTF-8"))

                // then
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getHeader("Location"))
                .isEqualTo("/admin/studydatas/" + engStudyDataId);
    }

    @Test
    void 영어공부데이터삭제() throws Exception {

        // given
        final Long engStudyDataId = 1L;

        // when
        final MvcResult result = mockMvc.perform(
                        delete("/admin/studydatas/" + engStudyDataId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-AUTH-TOKEN", "jwt")
                                .characterEncoding("UTF-8"))

                // then
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();
    }
}
