package com.seil.englishstudy.service.user;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.json.gson.GsonFactory;
import com.seil.englishstudy.repository.UserRepository;
import com.seil.englishstudy.web.rest.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
    private GoogleAuthVerifyService googleAuthVerifyService;
    @Mock
    private JwtProvider jwtProvider;

    @Test
    void 회원가입() throws Exception {

        // given
        final String idToken = "idToken";

        final GoogleIdToken googleIdToken = null;

        given(googleAuthVerifyService.getGoogleIdToken(idToken))
                .willReturn(googleIdToken);
        given(googleAuthVerifyService.verifyIdToken(googleIdToken))
                .willReturn(true);
        given(googleIdToken.getPayload())
                .willReturn(null);
        given(googleIdToken.getPayload().getEmail())
                .willReturn("test@gmail.com");
        given(googleIdToken.getPayload().get("name").toString())
                .willReturn("test");

        // when
        final String jwt = authService.signIn(idToken);

        // then
        assertThat(jwt).isNotNull();
    }

    @Test
    void 로그인() {

    }

    @Test
    void 회원탈퇴() {

    }
}
