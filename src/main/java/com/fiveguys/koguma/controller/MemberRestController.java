package com.fiveguys.koguma.controller;


import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MemberRestController {
    private final MemberService memberService;

    // 회원가입
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

    // 회원정보 수정
    @PutMapping("/member/update/{id}")
    public ResponseEntity<MemberDTO> update(
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
    }

    // 회원 삭제
    @PutMapping("/member/delete/{id}")
    public void deleteMember(@RequestBody MemberDTO memberDTO) {
        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long authenticatedUserId = // 인증에서 사용자 ID를 추출하는 로직;

        // 인증된 사용자가 계정 소유자인지 확인
        if (authenticatedUserId.equals(memberDTO.getId())) {
            memberService.deleteMember(memberDTO);
        } else {
            // 권한이 없는 삭제 시도를 처리
        }*/
        memberService.deleteMember(memberDTO);
    }

    //회원 정보 가져오기
    @GetMapping("/member/get/{id}")
    public ResponseEntity<MemberDTO> get(@PathVariable Long id) {
        MemberDTO existingMember = memberService.getMember(id);
        if (existingMember == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(existingMember);
        }
        return ResponseEntity.ok(existingMember);
    }

}
