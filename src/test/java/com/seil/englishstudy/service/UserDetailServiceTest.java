package com.seil.englishstudy.service;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
public class UserDetailServiceTest {

    @InjectMocks
    private CustomUserDetailService customUserDetailService;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Test
    void 유저데이터조회() {

        // given
        final User user = User.builder()
                            .uid("test@gmail.com")
                            .name("test_name")
                            .password("")
                            .roles(Arrays.asList("ROLE_USER"))
                            .build();

        given(userJpaRepository.findByUid("test@gmail.com"))
                .willReturn(user);

        // when
        final UserDetails load_data = customUserDetailService.loadUserByUsername("test@gmail.com");

        // then
        assertThat(load_data)
                .isNotNull();
        assertThat(load_data.getUsername())
                .isEqualTo(user.getUsername());
    }
}
