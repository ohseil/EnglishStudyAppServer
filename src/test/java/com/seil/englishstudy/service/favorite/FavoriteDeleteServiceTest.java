package com.seil.englishstudy.service.favorite;

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
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class FavoriteDeleteServiceTest {

    @InjectMocks
    private FavoriteDeleteService favoriteDeleteService;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Test
    void 즐겨찾기삭제() {

        // given
        final Long favoriteId = 1L;

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

        user.addFavorite(favorite);

        given(favoriteRepository.findById(favoriteId))
                .willReturn(Optional.of(favorite));

        // when
        favoriteDeleteService.deleteFavoriteForUser(favoriteId, user);

        // then
    }
}
