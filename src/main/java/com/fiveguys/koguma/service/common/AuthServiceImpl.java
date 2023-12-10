package com.fiveguys.koguma.service.common;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveguys.koguma.data.dto.KakaoAuthDTO;
import com.fiveguys.koguma.data.dto.KakaoProfileDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${security.kakao.clientId}")
    private String clientId;
    @Value("${security.kakao.redirectUri}")
    private String redirectUri;
    @Value("${security.expirationTime}")
    private String expirationTime;
    @Value("${security.refreshExpirationTime}")
    private String refreshExpirationTime;
    @Value("${security.secret}")
    private String secret;


    public KakaoAuthDTO getAccessToken(String code) {

        // POST 방식으로 key=value 데이터 요청
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        // HttpHeader 와 HttpBody 정보를 하나의 오브젝트에 담음
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청 (POST 방식) 후, response 변수의 응답을 받음
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // JSON 응답을 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoAuthDTO kakaoAuthDTO = null;
        try {
            kakaoAuthDTO = objectMapper.readValue(accessTokenResponse.getBody(), KakaoAuthDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoAuthDTO;
    }

    public KakaoProfileDTO findProfile(String token) {
        // POST 방식으로 key=value 데이터 요청
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader 와 HttpBody 정보를 하나의 오브젝트에 담음
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        // Http 요청 (POST 방식) 후, response 변수의 응답을 받음
        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        System.out.println(kakaoProfileResponse.getBody());
        // JSON 응답을 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfileDTO kakaoProfileDTO = null;
        try {
            kakaoProfileDTO = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfileDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfileDTO;
    }
    public boolean validateSocialMember(MemberDTO memberDTO){

        if (memberDTO.getEmail() == null){
            return false;
        }
        return true;
    }


    public String generateAccessToken(Long cliamId) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        String accessToken = Jwts.builder()
                .setHeaderParam("typ", "JWT") // 헤더에 타입 설정
                .setHeaderParam("alg", "HS512") // 헤더에 알고리즘 설정
                .setIssuer("ung")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(expirationTime)))
                .claim("id", cliamId)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return accessToken;
    }

    public String generateAccessTokenFromRefreshToken(Long refreshTokenClaim) {
        // Jwt 생성 후 헤더에 추가해서 보내줌
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        String accessToken = Jwts.builder()
                .setHeaderParam("typ", "JWT") // 헤더에 타입 설정
                .setHeaderParam("alg", "HS512") // 헤더에 알고리즘 설정
                .setIssuer("ung")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(expirationTime)))
                .claim("id", refreshTokenClaim)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return accessToken;
    }

    public String generateRefreshToken(Long cliamId) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .setIssuer("ung")
                .setIssuedAt(new Date(System.currentTimeMillis())) // 토큰 발급 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(refreshExpirationTime)))
                .claim("id", cliamId)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public void setSecurityContextHolder(HttpServletRequest request, MemberDTO memberDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        memberDTO,memberDTO.getPw(),
                        List.of(new SimpleGrantedAuthority(String.valueOf(memberDTO.getMemberRoleType()))));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public boolean validateTokenExpiration(String token) throws Exception {   // 기한이 안넘었으면 false
        //  넘었으면 true
        try {
            // 토큰 파싱
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token);

            return claims.getBody().getExpiration().before(new Date()); // 현재보다 만료가 이전인지 확인
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            throw new Exception(String.valueOf(HttpStatus.UNAUTHORIZED)); // 만약 올바르지 않은 토큰이라면 에러
        }
    }

    public Long getMemberId(String token) throws Exception {
        try {
            // 토큰 파싱
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token);
            Long memberId = claims.getBody().get("id", Long.class);
            return memberId;
        } catch (Exception e) {
            throw new Exception(String.valueOf(HttpStatus.UNAUTHORIZED)); // 만약 올바르지 않은 토큰이라면 에러
        }
    }

    public Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(14 * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        return cookie;
    }
    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName());
                if ("refreshToken".equals(cookie.getName())) {

                    return cookie.getValue();
                }
            }
        }
        return null; // 쿠키가 존재하지 않는 경우
    }
    @Override
    public MemberDTO getAuthMember() throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof MemberDTO) {
            MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();
            return memberDTO;
        } else {
            throw new Exception("인증 정보가 없습니다.");

        }
    }
}