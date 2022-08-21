package com.seil.englishstudy.service.admin;

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

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class AdminUserDeleteServiceTest {

    @InjectMocks
    private AdminUserDeleteService adminUserDeleteService;

    @Mock
    private UserRepository userRepository;

    @Test
    void 관리자계정삭제() {

        // given
        final String adminEmail = "testAdmin@gmail.com";

        final User foundUser = User.builder()
                                    .id(1L)
                                    .email("testAdmin@gmail.com")
                                    .name("testAdmin")
                                    .roleList(new HashSet<>(Arrays.asList("ROLE_USER", "ROLE_ADMIN")))
                                    .createdDate(LocalDateTime.now())
                                    .build();


        given(userRepository.findByEmail(adminEmail))
                .willReturn(foundUser);

        // when
        adminUserDeleteService.deleteAdminUser(adminEmail);

        // then
    }
}
