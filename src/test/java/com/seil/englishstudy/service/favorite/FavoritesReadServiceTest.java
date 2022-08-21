package com.seil.englishstudy.service.favorite;

import com.seil.englishstudy.dto.response.FavoritesReadResponse;
import com.seil.englishstudy.entity.EngStudyData;
import com.seil.englishstudy.entity.Favorite;
import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.repository.FavoriteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class FavoritesReadServiceTest {

    @InjectMocks
    private FavoritesReadService favoritesReadService;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Test
    void 유저의모든즐겨찾기조회() {

        // given
        final User user = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .name("test")
                .roleList(new HashSet<>(Arrays.asList("ROLE_USER", "ROLE_ADMIN")))
                .build();

        final EngStudyData engStudyData = EngStudyData.builder()
                .id(1L)
                .categoryCode(1)
                .question("question")
                .answer("answer")
                .build();

        final Favorite favorite = Favorite.builder()
                .id(1L)
                .user(user)
                .engStudyData(engStudyData)
                .build();

        final List<FavoritesReadResponse> favoritesReadResponseList = Arrays.asList(
                FavoritesReadResponse.builder()
                        .favoriteId(1L)
                        .categoryCode(1)
                        .question("question")
                        .answer("answer")
                        .build()
        );

        user.addFavorite(favorite);

        // when
        final List<FavoritesReadResponse> result = favoritesReadService.getFavoritesForUser(user);

        // then
        assertThat(result).isEqualTo(favoritesReadResponseList);
    }
}
