package com.seil.englishstudy.web.rest.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

/*
jwt 검증 method 로그 작성 예정
try 코드가 이전 코드와 같이 동작하는지 테스트 필요
 */

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private long tokenValidMilisecond = 1000L * 60 * 60; // 1시간만 토큰 유효

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createJwt(String userId, Set<String> roleList) {

        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roleList);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();
    }

    public Authentication getAuthentication(String jwt) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt 토큰에서 회원 구별 정보 추출
    public String getUserPk(String jwt) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody().getSubject();
    }

    // Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwt) {

        if (jwt == null)
            return false;

        // 주석 코드는 이전 코드
        // 아래의 try 코드가 이전 코드와 같이 동작하는지 테스트 필요
        /*Jws<Claims> claims;

        if (claims.getBody().getExpiration().before(new Date()) == true)
            return false;*/

        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
        } catch (SignatureException ex) {
            //throw new SigninFailedException(HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_NOT_VALID, "invalid jwt signature.");
            return false;
        } catch (MalformedJwtException ex) {
            //throw new SigninFailedException(HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_NOT_VALID, "invalid jwt token.");
            return false;
        } catch (ExpiredJwtException ex) {
            //throw new SigninFailedException(HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_NOT_VALID, "expired jwt.");
            return false;
        } catch (UnsupportedJwtException ex) {
            //throw new SigninFailedException(HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_NOT_VALID, "unsupported jwt.");
            return false;
        } catch (IllegalArgumentException ex) {
            //throw new SigninFailedException(HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_NOT_VALID, "jwt claims is empty.");
            return false;
        }

        return true;
    }

}
