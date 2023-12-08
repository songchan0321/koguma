package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.KakaoAuthDTO;
import com.fiveguys.koguma.data.dto.KakaoProfileDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Member;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    KakaoAuthDTO getAccessToken(String code);
    KakaoProfileDTO findProfile(String token);
//    SaveUserAndGetToken(String token);
    String generateAccessToken(Long cliamId);
    String generateAccessTokenFromRefreshToken(Long refreshTokenClaim);
    String generateRefreshToken(Long cliamId);
    void setSecurityContextHolder(HttpServletRequest request, MemberDTO memberDTO);
    boolean validateTokenExpiration(String token) throws Exception;
    Long getMemberId(String token);
    Cookie createCookie(String name, String value);
    String getRefreshTokenFromCookie(HttpServletRequest request);

    boolean validateSocialMember(MemberDTO memberDTO);
    MemberDTO getAuthMember();
}
