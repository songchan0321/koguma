package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.password.PasswordEncoder;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional

public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;



    @Override
    public void addMember(MemberDTO memberDTO, String nickname, Long pw, Number phone, float score, String email, Boolean roleFlag, Boolean socialFlag) {
        //Long rawPassword = memberDTO.getPw(); // 사용자가 입력한 비밀번호
        //String encodedPassword = passwordEncoder.encode(rawPassword); // 비밀번호 해싱
        memberDTO.setNickname(nickname);
        memberDTO.setPw(pw);
        //memberDTO.setPw(encodedPassword);
        memberDTO.setPhone(phone);
        memberDTO.setScore(36.5F);
        memberDTO.setEmail(email);
        memberDTO.setRoleFlag(false);
        memberDTO.setSocialFlag(false);

        // 닉네임 중복 체크
        Member existingMember = memberRepository.findByNickname(memberDTO.getNickname())
                .orElse(null);

        if (existingMember != null) {
            throw new RuntimeException("이미 사용 중인 닉네임입니다.");
        }
        Member addMember = (Member) memberRepository.findById(memberDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        addMember.setNickname(memberDTO.getNickname());
        memberRepository.save(addMember);

    }

    @Override
    public void deleteMember(MemberDTO memberDTO) {
        memberDTO.setNickname(null);
        memberDTO.setPw(null);
        memberDTO.setPhone(null);
        memberDTO.setScore(null);
        memberDTO.setEmail(null);
        memberDTO.setRoleFlag(false);
        memberDTO.setSocialFlag(false);
    }

    @Override
    public MemberDTO login(MemberDTO memberDTO) {
        Optional<Member> findByNickname = memberRepository.findByNickname(memberDTO.getNickname());
        if (findByNickname.isPresent()) {
            Member member = (Member) findByNickname.get();
            Long encodedPassword = member.getPw();
            //if (passwordEncoder.matches(memberDTO.getPw(), encodedPassword)){
                //return MemberDTO.toMemberDTO(member);
            //} else {
                //return null;
           // }
        } else {
            return null;
        }

        return memberDTO;
    }

    @Override
    public void logout() {

    }

    @Override
    public boolean validationCheckPw(MemberDTO memberDTO, String pw){
        return false;
    }

    @Override
    public MemberDTO getMember(MemberDTO memberDTO) {
        return MemberDTO.fromEntity(memberRepository.findById(memberDTO.getId())
                .orElseThrow(() -> new RuntimeException("해당 ID의 회원이 존재하지 않습니다.")));
    }

    @Override
    public MemberDTO getOtherMember(String nickname) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        return optionalMember.map(member -> MemberDTO.fromEntity((Member) member)).orElse(null);
    }

    @Override
    public void updateMember(Long id, String nickname, Long imageId) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 회원이 존재하지 않습니다."));

        // 닉네임 중복 체크
        Member existingMember = memberRepository.findByNickname(nickname)
                .orElse(null);

        if (existingMember != null && !existingMember.getId().equals(id)) {
            throw new RuntimeException("이미 사용 중인 닉네임입니다.");
        }

        member.setNickname(nickname);
        member.setImageId(imageId);

        memberRepository.save(member);
    }

    @Override
    public void updateMember(MemberDTO memberDTO) {

        memberRepository.save(memberDTO.toEntity());
    }

    @Override
    public void listMember(MemberDTO memberDTO) {

    }
}