package com.seil.englishstudy.service.favorite;

import com.seil.englishstudy.entity.EngStudyData;
import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.repository.EngStudyDataRepository;
import com.seil.englishstudy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/*
Favorite는 관계 데이터다.
User의 pk를 fk로 가지고 있다.
User의 속성 중 Set<Favorite>는 orphanremoval = true 속성이 설정 되어있다.
그래서 실제 서비스 코드에서는 User.addFavorite(favorite) 하나로 favorite에 영속성이 부여되고 id가 생성된다.
그런데 테스트 코드에서는 User.addFavorite() 함수를 관리할 수 없다.
그래서 이 함수의 결과인 id를 테스트용으로 부여할 수가 없다.
이 문제를 해결하려면 그냥 실제 서비스 코드를 userRepository.save(user), favoriteRepository.save(favorite)
이런식으로 수정하면 되지만 기존 코드로 해결할 수는 없는지 방법을 찾는중이다.
 */

@ExtendWith(SpringExtension.class)
public class FavoriteAddServiceTest {

    @InjectMocks
    private FavoriteAddService favoriteAddService;

    @Mock
    private EngStudyDataRepository engStudyDataRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    void 즐겨찾기추가() {

        // given
        final Long engStudyDataId = 1L;

        final User user = User.builder()
                                .id(1L)
                                .email("test@gamil.com")
                                .name("test")
                                .build();

        final EngStudyData foundEngStudyData = EngStudyData.builder().build();

        given(engStudyDataRepository.findById(engStudyDataId))
                .willReturn(Optional.of(foundEngStudyData));

        // when
        final Long favoriteId = favoriteAddService.addFavoriteForUser(engStudyDataId, user);

        // then
        assertThat(favoriteId).isNull();
    }
}
