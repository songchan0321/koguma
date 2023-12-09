package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.KakaoAuthDTO;
import com.fiveguys.koguma.data.dto.KakaoProfileDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.service.common.AuthService;
import com.fiveguys.koguma.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthRestController {

    private final MemberService memberService;
    private final AuthService authService;

    @Value("${security.secret}")
    private String secret;
    @Value("${security.headerString}")
    private String headerString;

    // 회원가입
    @PostMapping("/login")
    public ResponseEntity<String> add(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(name = "id") String id,
                                         @RequestParam(name="pw") String pw) {

        HttpHeaders headers = new HttpHeaders();

        MemberDTO memberDTO = memberService.login(id,pw);

        String accessToken =authService.generateAccessToken(memberDTO.getId());
        String refreshToken = authService.generateRefreshToken(memberDTO.getId());

        headers.set("Authorization", "Bearer " + accessToken);
        authService.setSecurityContextHolder(request,memberDTO);

        response.addCookie(authService.createCookie("refreshToken", refreshToken));
//        return new ResponseEntity<>(accessToken,HttpStatus.OK);
        return ResponseEntity.ok().headers(headers).body("");
    }

    @GetMapping("/error")
    public ResponseEntity<String> handleUnauthorized() {
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/kakao/callback/check")
    public ResponseEntity getLogin(@RequestParam("code") String code,
                                    HttpServletRequest request, HttpServletResponse response ) { //(1)


        ResponseEntity responseEntity = null;
        String accessToken = null;
        String refreshToken = null;

        HttpHeaders headers = new HttpHeaders();

        System.out.println("code : "+code);
        // 넘어온 인가 코드를 통해 access_token 발급
        KakaoAuthDTO kakaoAuthDTO = authService.getAccessToken(code);
        System.out.println(kakaoAuthDTO);
        KakaoProfileDTO kakaoProfileDTO = authService.findProfile(kakaoAuthDTO.getAccess_token());
        MemberDTO memberDTO = memberService.getMemberByEmail(kakaoProfileDTO.getKakao_account().getEmail());

        boolean valid = authService.validateSocialMember(memberDTO);
        if (valid){

            Member member = memberDTO.toEntity();
            member.setEmail(kakaoProfileDTO.getKakao_account().getEmail());
            member.setSocialFlag(true);
            accessToken = authService.generateAccessToken(member.getId());
            refreshToken = authService.generateRefreshToken(member.getId());

            headers.set("Authorization", "Bearer " + accessToken);
            authService.setSecurityContextHolder(request,memberDTO);

            response.addCookie(authService.createCookie("refreshToken", refreshToken));
            responseEntity = ResponseEntity.ok().headers(headers).body(accessToken);
        }
        else{
            responseEntity = (ResponseEntity) ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(kakaoProfileDTO.getKakao_account().getEmail());
        }

        //1. 이메일이 존재하는 회원 -> 로그인
        //2. 이메일이 없는 회원 -> 회원가입 후 연동


        return responseEntity;
    }
//    @PostMapping("/signup")
//    public ResponseEntity<MemberDTO> signUp(@RequestBody MemberDTO memberDTO,
//                                            HttpServletRequest request, HttpServletResponse response){
//
//        HttpHeaders headers = new HttpHeaders();
//
//        if (!memberService.nicknameValidationCheck(nickname)) {
//            return ResponseEntity.badRequest().build();
//        }
//        if (memberDTO.getEmail()!=null){
//            memberDTO.setSocialFlag(true);
//        }
//        memberService.addMember(memberDTO);  //addMember 수정 필요
//        String accessToken =authService.generateAccessToken(memberDTO.getId());
//        String refreshToken = authService.generateRefreshToken(memberDTO.getId());
//
//        headers.set("Authorization", "Bearer " + accessToken);
//        authService.setSecurityContextHolder(request,memberDTO);
//
//        response.addCookie(authService.createCookie("refreshToken", refreshToken));
//        return ResponseEntity.ok().headers(headers).body(memberDTO);
//
//    }


}
