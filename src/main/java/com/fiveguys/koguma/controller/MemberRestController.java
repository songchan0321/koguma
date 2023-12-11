package com.fiveguys.koguma.controller;


import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/member")
public class MemberRestController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/add")
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

    // 회원정보 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@CurrentMember MemberDTO authenticatedMember, @RequestBody MemberDTO memberDTO) {
        if (authenticatedMember == null || !authenticatedMember.getId().equals(memberDTO.getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 여기서 회원 정보 수정 로직을 수행
        memberService.updateMember(memberDTO, memberDTO.getNickname());

        return ResponseEntity.ok().build();
    }
    /*public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody MemberDTO memberDTO
    ) {
        MemberDTO member = memberService.getMember(id);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        MemberDTO existingMember = memberService.getMember(id);
        if (existingMember != null && !existingMember.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(existingMember);
        }
        memberService.updateMember(member, id, memberDTO.getNickname(), memberDTO.getImageId());
        return ResponseEntity.ok().build();
    }*/

    // 회원 삭제
    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id, @AuthenticationPrincipal MemberDTO authenticatedMember) {
        if (authenticatedMember != null && id.equals(authenticatedMember.getId())) {
            memberService.deleteMember(id);
            return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
        } else {
            // 권한이 없는 삭제 시도를 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회원 삭제 권한이 없습니다.");
        }
    }

    //회원 정보 가져오기
    @GetMapping("/profile/get")
    public ResponseEntity<MemberDTO> get(
            @CurrentMember MemberDTO memberDTO
    ) {
//        MemberDTO existingMember = memberService.getMember(id);
        if (memberDTO == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(memberDTO);
        }
        return ResponseEntity.ok(memberDTO);
    }

    @GetMapping("/member/profile/get/{id}")
    public ResponseEntity<MemberDTO> profile(@PathVariable Long id) {
        MemberDTO existingMember = memberService.getMember(id);
        if (existingMember == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(existingMember);
        }
        return ResponseEntity.ok(existingMember);
    }

}