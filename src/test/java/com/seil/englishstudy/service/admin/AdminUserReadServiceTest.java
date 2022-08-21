package com.seil.englishstudy.service.admin;

import com.seil.englishstudy.dto.response.UserReadResponse;
import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class AdminUserReadServiceTest {

    @InjectMocks
    private AdminUserReadService adminUserReadService;

    @Mock
    private UserRepository userRepository;

    @Test
    void 모든유저조회() {

        // given
        final List<User> foundUserList = Arrays.asList(
                User.builder()
                        .id(1L)
                        .email("test1@gmail.com")
                        .name("test1")
                        .roleList(new HashSet<>(Arrays.asList("ROLE_USER")))
                        .build(),
                User.builder()
                        .id(2L)
                        .email("test2@gmail.com")
                        .name("test2")
                        .roleList(new HashSet<>(Arrays.asList("ROLE_USER")))
                        .build()
        );

        final List<UserReadResponse> userReadResponseList = Arrays.asList(
                UserReadResponse.builder()
                                .userId(1L)
                                .email("test1@gmail.com")
                                .name("test1")
                                .roleList(new HashSet<>(Arrays.asList("ROLE_USER")))
                                .favoriteList(new HashSet<>())
                                .build(),
                UserReadResponse.builder()
                                .userId(2L)
                                .email("test2@gmail.com")
                                .name("test2")
                                .roleList(new HashSet<>(Arrays.asList("ROLE_USER")))
                                .favoriteList(new HashSet<>())
                                .build()
        );

        given(userRepository.findAll())
                .willReturn(foundUserList);

        // when
        final List<UserReadResponse> result = adminUserReadService.readAllUser();

        // then
        assertThat(result).isEqualTo(userReadResponseList);
    }

}
