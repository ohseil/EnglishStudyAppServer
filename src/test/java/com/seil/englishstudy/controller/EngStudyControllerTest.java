package com.seil.englishstudy.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seil.englishstudy.dto.response.EngStudyPageResponse;
import com.seil.englishstudy.dto.response.FavoritesReadResponse;
import com.seil.englishstudy.service.study.EngStudyPageService;
import com.seil.englishstudy.web.rest.EngStudyController;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EngStudyController.class)
public class EngStudyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EngStudyPageService engStudyPageService;

    @MockBean
    private JwtProvider jwtProvider;

    @Test
    @WithMockUser
    void 페이지조회() throws Exception {

        // given
        final long categoryCode = 1;
        final List<EngStudyPageResponse> engStudyPageResponseList = new ArrayList<>();

        given(engStudyPageService.getEngStudyPage(categoryCode))
                .willReturn(engStudyPageResponseList);

        // when
        final MvcResult result = mockMvc.perform(
                        get("/studydatas")
                                .param("categoryCode", String.valueOf(categoryCode))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-AUTH-TOKEN", "jwt")
                                .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        final List<FavoritesReadResponse> response = new ObjectMapper()
                                                            .readValue(result.getResponse().getContentAsString(),
                                                                        new TypeReference<List<FavoritesReadResponse>>(){});

        assertThat(response)
                .isEqualTo(engStudyPageResponseList);
    }
}
