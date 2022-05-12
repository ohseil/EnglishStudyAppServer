package com.seil.englishstudy.web.rest.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
                .csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증하므로 세션은 필요없으므로 생성안함.
                .and()
                .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
                .antMatchers("/signin").permitAll() // 가입 및 인증 주소는 누구나 접근가능
                .antMatchers("/*/admin/**").hasRole("ADMIN") // uri에 /admin/을 포함하면 관리자만 접근 가능
                .anyRequest().hasRole("USER") // 그외 나머지 요청은 모두 인증된 회원만 접근 가능
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt token 필터를 id/password 인증 필터 전에 넣는다

    }

    @Override // ignore check swagger resource
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/swagger/**", "/webjars/**");

    }
}