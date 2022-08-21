package com.seil.englishstudy.service.admin;

import com.seil.englishstudy.dto.request.AdminUserCreateRequest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class AdminUserCreateServiceTest {

    @InjectMocks
    private AdminUserCreateService adminUserCreateService;

    @Mock
    private UserRepository userRepository;

    @Test
    void 관리자계정생성() {

        // given
        final Long userId = 1L;

        final AdminUserCreateRequest adminUserCreateRequest = AdminUserCreateRequest.builder()
                                                                                    .email("testAdmin@gmail.com")
                                                                                    .name("testAdmin")
                                                                                    .build();

        final User user = User.builder()
                            .email("testAdmin@gmail.com")
                            .name("testAdmin")
                            .roleList(new HashSet<>(Arrays.asList("ROLE_USER", "ROLE_ADMIN")))
                            .createdDate(LocalDateTime.now())
                            .build();

        final User createdUser = User.builder()
                                    .id(userId)
                                    .email("testAdmin@gmail.com")
                                    .name("testAdmin")
                                    .roleList(new HashSet<>(Arrays.asList("ROLE_USER", "ROLE_ADMIN")))
                                    .createdDate(LocalDateTime.now())
                                    .build();

        given(userRepository.save(user))
                .willReturn(createdUser);

        // when
        final Long result = adminUserCreateService.createAdminUser(adminUserCreateRequest);

        // then
        assertThat(result).isEqualTo(userId);
    }
}
