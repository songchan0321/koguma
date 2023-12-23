package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.MemberSearchByLocationDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.repository.common.QueryRepository;
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
    private final QueryRepository queryRepository;


    @Override
    public Member addMember(MemberDTO memberDTO, String nickname, String pw, String phone, float score, String email, Boolean roleFlag, Boolean socialFlag, String profileURL) {
        memberDTO.setNickname(nickname);
        memberDTO.setPw(pw);
        memberDTO.setPhone(phone);
        memberDTO.setScore(36.5F);
        memberDTO.setEmail(email);
        memberDTO.setRoleFlag(false); // false = 일반 회원, true = 관리자
        memberDTO.setSocialFlag(false);
        memberDTO.setActiveFlag(true);
        memberDTO.setProfileURL("https://koguma.kr.object.ncloudstorage.com/defaultMan.jpg");

        // 닉네임 중복 체크
        Member existingMember = memberRepository.findByNicknameAndActiveFlag(memberDTO.getNickname(), memberDTO.getActiveFlag())
                .orElse(null);

        if (existingMember != null) {
            throw new RuntimeException("이미 사용 중인 닉네임입니다.");
        }

        // 중복이 없는 경우에만 저장
        memberRepository.save(memberDTO.toEntity());

        // 저장된 회원 정보를 반환
        return memberRepository.findByNicknameAndActiveFlag(memberDTO.getNickname(), memberDTO.getActiveFlag())
                .orElse(null);
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
        memberDTO.setProfileURL(otherMember.getProfileURL());

        return memberDTO;
    }

    @Override
    public MemberDTO updateMember(MemberDTO memberDTO, String nickname, String profileURL) {
        Long id = memberDTO.getId();

        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        Member conflictingMember = memberRepository.findByNicknameAndActiveFlag(nickname, true)
                .orElse(null);

        if (conflictingMember != null && !conflictingMember.getId().equals(id)) {
            throw new RuntimeException("이미 사용 중인 닉네임입니다.");
        }

        existingMember.setNickname(nickname);
        existingMember.setProfileURL(profileURL);

        return MemberDTO.fromEntity(memberRepository.save(existingMember));
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

    @Override
    public void setScore(float culculateScore, MemberDTO memberDTO) {
        float oldScore = memberDTO.getScore();

        // 부호에 따라 다르게 처리
        if (culculateScore > 0) {
            float newScore = oldScore + culculateScore;

            // 100 이하일 때만 업데이트
            if (newScore <= 100) {
                memberDTO.setScore(newScore);
                memberRepository.save(memberDTO.toEntity());
            }
        } else if (culculateScore < 0) {
            float newScore = oldScore - Math.abs(culculateScore);

            // 0 이상일 때만 업데이트
            if (newScore >= 0) {
                memberDTO.setScore(newScore);
                memberRepository.save(memberDTO.toEntity());
            }
        }
    }

    @Override
    public List<MemberSearchByLocationDTO> searchByLocationMember(LocationDTO locationDTO,String keyword) throws Exception {
        List<Member> memberList =queryRepository.findAllByDistanceMember(locationDTO,keyword);
        List<MemberSearchByLocationDTO> memberDTOList = memberList.stream().map(MemberSearchByLocationDTO::fromEntity).collect(Collectors.toList());

        return memberDTOList;
    }

    public MemberDTO getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberDTO::fromEntity)
                .orElse(null);
    }



}

