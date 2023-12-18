package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.KakaoAuthDTO;
import com.fiveguys.koguma.data.dto.KakaoProfileDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.SmsDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.service.common.AuthService;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.common.SmsService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/auth")
@Log4j2
public class AuthRestController {

    private final MemberService memberService;
    private final AuthService authService;
    private final SmsService smsService;

    @Value("${security.secret}")
    private String secret;
    @Value("${security.headerString}")
    private String headerString;

    // 회원가입
    @PostMapping("/login")
    public ResponseEntity<String> add(HttpServletRequest request, HttpServletResponse response,
                                          @RequestBody Map<String,String> loginform) {
        try {
            HttpHeaders headers = new HttpHeaders();

            MemberDTO memberDTO = memberService.login(loginform.get("id"), loginform.get("pw"));

            String accessToken =authService.generateAccessToken(memberDTO.getId());
            String refreshToken = authService.generateRefreshToken(memberDTO.getId());
            headers.set("Authorization", "Bearer " + accessToken);
            authService.setSecurityContextHolder(request,memberDTO);

            response.addCookie(authService.createCookie("refreshToken", refreshToken));
            return ResponseEntity.ok().headers(headers).body(accessToken);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("로그인 실패: " + e.getMessage());
        }
    }

    @GetMapping("/error")
    public ResponseEntity<String> handleUnauthorized() {
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/kakao/callback/check")
    public ResponseEntity getLogin(@RequestParam("code") String code,
                                    @CurrentMember MemberDTO loginInfo,
                                    HttpServletRequest request, HttpServletResponse response ) throws Exception { //(1)


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
        if (valid){   // 소셜로그인으로 로그인

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
        //스프링 컨텍스트에 저장된 인증정보가 있다면 설정 페이지에서 카카오 연동을 할 수 있음.
        else if (loginInfo!=null){
            loginInfo.setSocialFlag(true);
            memberService.updateMember(loginInfo);
        }
        else{  // 연동된 소셜로그인이 없어 회원가입부터
            responseEntity = (ResponseEntity) ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(kakaoProfileDTO.getKakao_account().getEmail());
        }

        return responseEntity;
    }

    @PostMapping("/member/add")
    public ResponseEntity<MemberDTO> add(@RequestBody MemberDTO memberDTO) {
        String nickname = memberDTO.getNickname();
        String pw = memberDTO.getPw();
        String phone = memberDTO.getPhone();
        float score = memberDTO.getScore();
        String email = memberDTO.getEmail();
        Boolean roleFlag = memberDTO.getRoleFlag();
        Boolean socialFlag = memberDTO.getSocialFlag();

        // 닉네임 중복 체크
        if (!memberService.nicknameValidationCheck(nickname)) {
            return ResponseEntity.badRequest().build();
        }

        // 중복이 아니면 회원 추가
        memberService.addMember(memberDTO, nickname, pw, phone, score, email, roleFlag, socialFlag);

        return ResponseEntity.ok().build();
    }
    @PostMapping("/sendSms")
    public String sendSms(@RequestBody SmsDTO smsDTO) throws Exception{
        log.info("/member/sendOne : POST : {}", smsDTO);
        smsService.sendSms(smsDTO);
        return "{\"success\": true}";
    }
    @PostMapping("/verifySms")
    public String verifySms(@RequestBody SmsDTO smsDTO) throws Exception{
        log.info("/member/verifySms : POST : {}", smsDTO);
        smsService.verifySms(smsDTO);
        return "{\"success\": true}";
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
