package com.seil.englishstudy.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seil.englishstudy.dto.response.FavoritesReadResponse;
import com.seil.englishstudy.service.favorite.FavoriteAddService;
import com.seil.englishstudy.service.favorite.FavoriteDeleteService;
import com.seil.englishstudy.service.favorite.FavoritesReadService;
import com.seil.englishstudy.web.rest.FavoriteController;
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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FavoriteController.class)
public class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoriteAddService favoriteAddService;
    @MockBean
    private FavoritesReadService favoritesReadService;
    @MockBean
    private FavoriteDeleteService favoriteDeleteService;

    @MockBean
    private JwtProvider jwtProvider;

    @Test
    @WithMockUser
    void 즐겨찾기추가() throws Exception {

        // given
        final Long engStudyDataId = 1L;
        final Long favoriteId = 1L;

        given(favoriteAddService.addFavoriteForUser(engStudyDataId, null))
                .willReturn(favoriteId);

        // when
        final MvcResult result = mockMvc.perform(
                        post("/favorites")
                                .content(engStudyDataId.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-AUTH-TOKEN", "jwt")
                                .characterEncoding("UTF-8"))

                // then
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getHeader("Location"))
                .isEqualTo("/favorites/" + favoriteId);
    }

    @Test
    @WithMockUser
    void 즐겨찾기조회() throws Exception {

        // given
        final List<FavoritesReadResponse> favoritesReadResponseList = new ArrayList<>();

        given(favoritesReadService.getFavoritesForUser(null))
                .willReturn(favoritesReadResponseList);

        // when
        final MvcResult result = mockMvc.perform(
                        get("/favorites")
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
                .isEqualTo(favoritesReadResponseList);
    }

    @Test
    @WithMockUser
    void 즐겨찾기삭제() throws Exception {

        // given
        final Long favoriteId = 1L;

        // when
        final MvcResult result = mockMvc.perform(
                        delete("/favorites/" + favoriteId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-AUTH-TOKEN", "jwt")
                                .characterEncoding("UTF-8"))

        // then
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();

    }
}
