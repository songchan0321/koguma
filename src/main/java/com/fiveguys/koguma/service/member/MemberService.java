package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Member;

public interface MemberService {


    void addMember(MemberDTO memberDTO, String nickname, String pw, String phone, float score, String email, Boolean roleFlag, Boolean socialFlag);

    void deleteMember(MemberDTO memberDTO);
    void updateMember(Long id, String nickname, Long imageId);
    void updateMember(MemberDTO memberDTO);
    boolean validationCheckPw(MemberDTO memberDTO, String pw);
    MemberDTO login(MemberDTO memberDTO);
    void logout();
    MemberDTO getMember(MemberDTO memberDTO);
    MemberDTO getOtherMember(String nickname);



    void listMember(MemberDTO memberDTO);

}