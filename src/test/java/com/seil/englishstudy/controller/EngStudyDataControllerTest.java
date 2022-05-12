package com.seil.englishstudy.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.seil.englishstudy.entity.EnglishStudyData;
import com.seil.englishstudy.service.*;
import com.seil.englishstudy.web.rest.EnglishStudyApiRest;
import com.seil.englishstudy.web.rest.config.security.JwtTokenProvider;
import com.seil.englishstudy.web.rest.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(controllers = EnglishStudyApiRest.class)
//@SpringBootTest
//@AutoConfigureMockMvc
@WithMockUser(username = "test@gmail.com")
public class EngStudyDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudyDataCreateService studyDataCreateService;
    @MockBean
    private StudyDataReadService studyDataReadService;
    @MockBean
    private StudyDataUpdateService studyDataUpdateService;
    @MockBean
    private StudyDataDeleteService studyDataDeleteService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    AuthenticationManager authenticationManager;

    @Test
    void 데이터생성() throws Exception {

        // given
        final DataCreateRequest request = DataCreateRequest.builder()
                                                .categoryCode(1)
                                                .question("question")
                                                .answer("answer")
                                                .build();

        given(studyDataCreateService.createStudyData(request))
                .willReturn(new DataCUDResponse("1"));

        // json 형태로 변경
        final String content = new ObjectMapper().writeValueAsString(request);

        // when
        final MvcResult result = mockMvc.perform(
                        post("/studydatas")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-AUTH-TOKEN", "token")
                                .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").exists())
                .andDo(print())
                .andReturn();

        final DataCUDResponse response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), DataCUDResponse.class);

        assertThat(response.getMsg())
                .isEqualTo("1");

    }

    @Test
    void 데이터변경() throws Exception {

        // given
        final DataUpdateRequest request = DataUpdateRequest.builder()
                                                .id(1L)
                                                .categoryCode(1)
                                                .question("question")
                                                .answer("answer")
                                                .build();

        given(studyDataUpdateService.updateStudyData(1L, request))
                .willReturn(new DataCUDResponse("success"));

        // json 형태로 변경
        final String content = new ObjectMapper().writeValueAsString(request);

        // when
        final MvcResult result = mockMvc.perform(
                        put("/studydatas/1")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-AUTH-TOKEN", "token")
                                .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").exists())
                .andDo(print())
                .andReturn();

        final DataCUDResponse response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), DataCUDResponse.class);

        assertThat(response.getMsg())
                .isEqualTo("success");
    }

    @Test
    void 데이터하나삭제() throws Exception {

        // given
        final Long request = 1L;

        given(studyDataDeleteService.deleteStudyData(request))
                .willReturn(new DataCUDResponse("success"));

        // json 형태로 변경
        final String content = new ObjectMapper().writeValueAsString(request);

        // when
        final MvcResult result = mockMvc.perform(
                        delete("/studydatas/1")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-AUTH-TOKEN", "token")
                                .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").exists())
                .andDo(print())
                .andReturn();

        final DataCUDResponse response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), DataCUDResponse.class);

        assertThat(response.getMsg())
                .isEqualTo("success");
    }

    @Test
    void 데이터전부삭제() throws Exception {

        // given
        given(studyDataDeleteService.deleteStudyDataAll())
                .willReturn(new DataCUDResponse("success"));

        // when
        final MvcResult result = mockMvc.perform(
                        delete("/studydatas/all")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-AUTH-TOKEN", "token")
                                .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").exists())
                .andDo(print())
                .andReturn();

        final DataCUDResponse response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), DataCUDResponse.class);

        assertThat(response.getMsg())
                .isEqualTo("success");
    }

    @Test
    void 데이터모두조회() throws Exception {

        // given
        final List<EnglishStudyData> dataList = Arrays.asList(
                new EnglishStudyData(1L, 1, "question", "answer")
        );

        given(studyDataReadService.readStudyDataAll())
                .willReturn(new DataReadResponse(dataList));


        // when
        final MvcResult result = mockMvc.perform(
                get("/studydatas/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", "token")
                        .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataList").exists())
                .andDo(print())
                .andReturn();

        final DataReadResponse response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), DataReadResponse.class);

        assertThat(response.getDataList())
                .isEqualTo(dataList);
    }

    @Test
    void 데이터카테고리별조회() throws Exception {

        // given
        final List<EnglishStudyData> dataList = Arrays.asList(
                new EnglishStudyData(1L, 1, "question", "answer")
        );

        given(studyDataReadService.readStudyDataByCategory(1))
                .willReturn(new DataReadResponse(dataList));

        // when
        final MvcResult result = mockMvc.perform(
                get("/studydatas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", "token")
                        .queryParam("categoryCode", "1")
                        .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataList").exists())
                .andDo(print())
                .andReturn();

        final DataReadResponse response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), DataReadResponse.class);

        assertThat(response.getDataList())
                .isEqualTo(dataList);
    }

    @Test
    void 데이터id리스트로여러개조회() throws Exception {

        // given
        final List<EnglishStudyData> dataList = Arrays.asList(
                new EnglishStudyData(1L, 1, "question", "answer"),
                new EnglishStudyData(2L, 1, "question", "answer")
        );

        given(studyDataReadService.readStudyDataByIds(Arrays.asList(1L, 2L)))
                .willReturn(new DataReadResponse(dataList));

        // when
        final MvcResult result = mockMvc.perform(
                get("/studydatas/1,2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", "token")
                        .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataList").exists())
                .andDo(print())
                .andReturn();

        final DataReadResponse response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), DataReadResponse.class);

        assertThat(response.getDataList())
                .isEqualTo(dataList);

    }

    @Test
    void 데이터pk조회() throws Exception {

        // given
        given(studyDataReadService.readPK(1, "question", "answer"))
                .willReturn(new DataReadPKResponse(1));

        // when
        final MvcResult result = mockMvc.perform(
                        get("/studydatas/ids")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-AUTH-TOKEN", "token")
                                .queryParam("categoryCode", "1")
                                .queryParam("question", "question")
                                .queryParam("answer", "answer")
                                .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pk").exists())
                .andDo(print())
                .andReturn();

        assertThat(new ObjectMapper().readValue(result.getResponse().getContentAsString(), DataReadPKResponse.class).getPk())
                .isEqualTo(1);
    }

}
