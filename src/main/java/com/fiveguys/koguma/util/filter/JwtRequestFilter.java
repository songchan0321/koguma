package com.fiveguys.koguma.util.filter;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.service.common.AuthService;
import com.fiveguys.koguma.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    private final AuthService authService;

    private final MemberService memberService;

    @Value("${security.tokenPrefix}")
    private String tokenPrefix;
    @Value("${security.secret}")
    private String secret;
    @Value("${security.headerString}")
    private String headerString;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtHeader = ((HttpServletRequest)request).getHeader(headerString);
        System.out.println("jwt : "+jwtHeader);
        boolean validToken = false;
        // header 가 정상적인 형식인지 확인
        if(jwtHeader == null || !jwtHeader.startsWith(tokenPrefix)) {
            filterChain.doFilter(request, response);
            return;
        }
        log.info("헤더 정상형식");
        // jwt 토큰을 검증해서 정상적인 사용자인지 확인
        String token = jwtHeader.replace(tokenPrefix, "").trim();
        Long memberId = null;
        try {
            log.info("token : "+token);
            validToken = authService.validateTokenExpiration(token);
            memberId = authService.getMemberId(token);
            log.info("validToken : "+validToken);
            log.info("memberId : "+memberId);
        } catch (Exception e) {
           log.info(e.getMessage());
        }
        //헤더에 accessToken을 받아서
        //claim의 id값은 있는데, 인증이 안된경우
        //1. 토큰 만료시간 넘지않을시 인증진행
        //2. 토큰 만료했을때
        //2-1 리프레시토큰으로 액세스토큰 생성후 인증
        //2-2 리프레시 토큰이 없으면 로그인진행



        if(!validToken && SecurityContextHolder.getContext().getAuthentication() == null){ 
            MemberDTO memberDTO = memberService.getMember(memberId);  //1. 액세스토큰 만료X, 인증 없을시
            authService.setSecurityContextHolder(request,memberDTO);
            log.info("1.엑세스토큰 만료 x");

        }
        else if (validToken){  //2. 액세스토큰 만료O
            String refreshToken = authService.getRefreshTokenFromCookie(request);
            log.info("토큰",token);
            log.info("리프레쉬토큰",refreshToken);
            log.info("2.엑세스토큰 만료 O");
            boolean valid = false;
            try {
                valid = authService.validateTokenExpiration(refreshToken);
            } catch (Exception e) {
                log.info(e.getMessage());
            }
            if (!valid) {     //2-1 리프레시토큰으로 엑세스 토큰 생성후 인증
                log.info("2-1 리프레시토큰으로 엑세스 토큰 생성후 인증");
                memberId = authService.getMemberId(refreshToken);
                MemberDTO memberDTO = memberService.getMember(memberId);
                authService.generateAccessTokenFromRefreshToken(memberId);
                authService.setSecurityContextHolder(request,memberDTO);
            }
        }

        log.info("필터체인 완료");
        filterChain.doFilter(request, response);
    }

}