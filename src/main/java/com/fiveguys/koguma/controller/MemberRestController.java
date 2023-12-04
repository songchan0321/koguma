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
            @RequestParam String nickname,
            @RequestParam Long imageId
    ) {
        // 해당 id의 회원 정보를 가져온다.
        MemberDTO member = memberService.getMember(id);

        // 회원이 존재하는지 확인
        if (member == null) {
            // 존재하지 않으면 예외 처리 또는 적절한 응답 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(member);
        }

        // 닉네임 중복 체크
        MemberDTO existingMember = memberService.getMember(id);
        if (existingMember != null && !existingMember.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(existingMember);
        }

        // 회원 정보를 업데이트 (nickname과 imageId만 업데이트)
        memberService.updateMember(existingMember, id, nickname, imageId);

        // 적절한 응답 반환
        return ResponseEntity.ok().build();
    }

    // 회원 삭제
    @PutMapping("/member/delete/{id}")
    public ResponseEntity<MemberDTO> delete(@PathVariable Long id) {
        MemberDTO existingMember = memberService.getMember(id);
        // 회원이 존재하는지 확인
        if (existingMember == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(existingMember);
        }
        // 회원을 삭제 (activeFlag를 false로 설정)
        memberService.deleteMember(existingMember);
        return ResponseEntity.ok().build();
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
