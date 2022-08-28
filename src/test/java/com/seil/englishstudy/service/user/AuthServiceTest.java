package com.seil.englishstudy.service.user;

import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.repository.UserRepository;
import com.seil.englishstudy.vo.GoogleProfile;
import com.seil.englishstudy.web.rest.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/*
google 인증 시스템 관련 외부 라이브러리의 class는 mocking이 안된다.
interface를 만들면 가능하다.
구조 설계해서 작성 예정이다.
 */

@ExtendWith(SpringExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private GoogleVerifyService googleVerifyService;
    @Mock
    private JwtProvider jwtProvider;

    @Test
    void 회원가입() {

        // given
        final String idToken = "idToken";

        final GoogleProfile googleProfile = GoogleProfile.builder()
                                                        .email("test@gmail.com")
                                                        .name("test")
                                                        .build();

        final User user = User.builder()
                            .email("test@gmail.com")
                            .name("test")
                            .roleList(new HashSet<>(Arrays.asList("ROLE_USER")))
                            .build();

        final String jwt = "jwt";

        given(googleVerifyService.verify(idToken))
                .willReturn(googleProfile);
        given(userRepository.findByEmail(googleProfile.getEmail()))
                .willReturn(null);
        given(jwtProvider.createJwt(String.valueOf(user.getId()), user.getRoleList()))
                .willReturn("jwt");

        // when
        final String result = authService.signIn(idToken);

        // then
        assertThat(result).isNotNull().isEqualTo("jwt");
    }

    @Test
    void 로그인() {

        // given
        final String idToken = "idToken";

        final GoogleProfile googleProfile = GoogleProfile.builder()
                .email("test@gmail.com")
                .name("test")
                .build();

        final User user = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .name("test")
                .roleList(new HashSet<>(Arrays.asList("ROLE_USER")))
                .build();

        final String jwt = "jwt";

        given(googleVerifyService.verify(idToken))
                .willReturn(googleProfile);
        given(userRepository.findByEmail(googleProfile.getEmail()))
                .willReturn(user);
        given(jwtProvider.createJwt(String.valueOf(user.getId()), user.getRoleList()))
                .willReturn("jwt");

        // when
        final String result = authService.signIn(idToken);

        // then
        assertThat(result).isNotNull().isEqualTo("jwt");
    }

}
