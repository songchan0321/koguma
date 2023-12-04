package com.fiveguys.koguma.controller;


import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.service.member.MemberService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<MemberDTO> update(@PathVariable MemberDTO memberDTO, Long id, String nickname, String imageId, Boolean activeFlag){
        memberService.getMember(memberDTO.getId());
        memberService.updateMember(memberDTO);
        return ResponseEntity.ok().build();
    }

    // 회원 삭제
    @DeleteMapping("/member/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // 해당 id의 회원을 찾아온다.
        MemberDTO existingMember = memberService.getMember(id);
        // 회원이 존재하는지 확인
        if (existingMember == null) {
            // 존재하지 않으면 예외 처리 또는 적절한 응답 처리
            return ResponseEntity.notFound().build();
        }
        // 회원을 삭제 (activeFlag를 false로 설정)
        memberService.deleteMember(existingMember);
        // 적절한 응답 반환
        return ResponseEntity.ok().build();
    }

    //회원 정보 가져오기
    @GetMapping("/member/get/{id}")
    public ResponseEntity<MemberDTO> get(@PathVariable Long id) {
        MemberDTO existingMember = memberService.getMember(id);
        if (existingMember == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(existingMember);
    }

}
