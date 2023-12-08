package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberDTO;

import java.util.List;

public interface MemberService {


    void addMember(MemberDTO memberDTO, String nickname, String pw, String phone, float score, String email, Boolean roleFlag, Boolean socialFlag);
    void deleteMember(MemberDTO memberDTO);
    void updateMember(MemberDTO memberDTO, Long id, String nickname, Long imageId, Boolean activeFlag);
    void updateMember(MemberDTO memberDTO);
    MemberDTO login(String id, String pw);
    boolean validationCheckPw(String rawPw, String encodedPw);
    void logout();
    MemberDTO getMember(Long id);
    MemberDTO getOtherMember(Long id);
    List<MemberDTO> listMember();
    MemberDTO getMemberByEmail(String email);


}

