package com.seil.englishstudy.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.seil.englishstudy.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;

@DataJpaTest
public class UserRepositoryTest {

    /*@Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void 유저정보조회() {

        // given
        final User user = User.builder()
                            .uid("test@gmail.com")
                            .name("test_name")
                            .password("")
                            .roles(Arrays.asList("ROLE_USER"))
                            .build();

        userJpaRepository.save(user);

        // when
        final UserDetails load_data = userJpaRepository.findByUid(user.getUsername());

        // then
        assertThat(load_data)
                .isNotNull();
        assertThat(load_data.getUsername())
                .isEqualTo("test@gmail.com");
    }*/
}
