package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.service.common.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional

public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;
    private final AuthService authService;


    @Override
    public Member addMember(MemberDTO memberDTO, String nickname, String pw, String phone, float score, String email, Boolean roleFlag, Boolean socialFlag) {
        //Long rawPw = memberDTO.getPw(); // 사용자가 입력한 비밀번호
        //String encodedPw = passwordEncoder.encode(rawPw); // 비밀번호 해싱
        memberDTO.setNickname(nickname);
        memberDTO.setPw(pw);
        //memberDTO.setPw(encodedPw);
        memberDTO.setPhone(phone);
        memberDTO.setScore(36.5F);
        memberDTO.setEmail(email);
        memberDTO.setRoleFlag(false); // false = 일반 회원, true = 관리자
        memberDTO.setSocialFlag(false);
        memberDTO.setActiveFlag(true);

        // 닉네임 중복 체크
        Member existingMember = memberRepository.findByNicknameAndActiveFlag(memberDTO.getNickname(), memberDTO.getActiveFlag())
                .orElse(null);

        if (existingMember != null) {
            throw new RuntimeException("이미 사용 중인 닉네임입니다.");
        }

        memberDTO.setNickname(memberDTO.getNickname());
        memberRepository.save(memberDTO.toEntity());

        return existingMember;
    }

    @Override
    public void deleteMember(Long id) {
        Optional<Member> existingMember = memberRepository.findByIdAndActiveFlag(id, true);

        existingMember.ifPresent(member -> {
            member.setActiveFlag(false);
            memberRepository.save(member);
        });
    }

    @Override
    public MemberDTO login(String id, String pw) throws Exception {  //id : nickname, email

        try {
            MemberDTO memberDTO = null;

            Member member = memberRepository.findByNicknameOrEmail(id, id)
                    .orElseThrow(() -> new Exception("회원이 존재하지 않습니다."));

            if (validationCheckPw(pw, member.getPw())) {
                memberDTO = MemberDTO.fromEntity(member);
                return memberDTO;
            } else {
                throw new Exception("패스워드가 일치하지 않습니다.");
            }
        } catch (Exception e) {
            throw new Exception("로그인 실패: " + e.getMessage());
        }
    }

    @Override
    public boolean validationCheckPw(String rawPw, String encodedPw) {

        return rawPw.equals(encodedPw);
    }


    @Override
    public void logout() {
        MemberDTO loggedInMember = (MemberDTO) httpSession.getAttribute("loggedInMember");
        if (loggedInMember != null) {

            httpSession.removeAttribute("loggedInMember");
        }
    }


    @Override
    public MemberDTO getMember(Long id) {
        return MemberDTO.fromEntity(memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 회원이 존재하지 않습니다.")));
    }

    @Override
    public MemberDTO getOtherMember(Long id) {
        Member otherMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 회원이 존재하지 않습니다."));

        // 다른 회원의 경우 닉네임과 사진만 반환
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setNickname(otherMember.getNickname());
        memberDTO.setImageId(otherMember.getImageId());

        return memberDTO;
    }

    @Override
    public void updateMember(MemberDTO memberDTO, String nickname) {
        Long id = memberDTO.getId();

        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        Member conflictingMember = memberRepository.findByNicknameAndActiveFlag(memberDTO.getNickname(), true)
                .orElse(null);

        if (conflictingMember != null && !conflictingMember.getId().equals(id)) {
            throw new RuntimeException("이미 사용 중인 닉네임입니다.");
        }

        existingMember.setNickname(memberDTO.getNickname());
        existingMember.setImageId(memberDTO.getImageId());

        memberRepository.save(existingMember);
    }

    @Override
    public void updateMember(MemberDTO memberDTO) {

        memberRepository.save(memberDTO.toEntity());
    }

    @Override
    public List<MemberDTO> listMember() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberDTO::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public boolean nicknameValidationCheck(String nickname) {
        Member existingMember = memberRepository.findByNicknameAndActiveFlag(nickname, true)
                .orElse(null);

        return existingMember == null;
    }
    public MemberDTO getMemberByEmail(String email) {
        return MemberDTO.fromEntity(memberRepository.findByEmail(email));
    }
}

