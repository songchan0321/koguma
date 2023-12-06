package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Member;

import java.util.List;

public interface MemberService {


    Member addMember(MemberDTO memberDTO, String nickname, String pw, String phone, float score, String email, Boolean roleFlag, Boolean socialFlag);
    void deleteMember(Long id);
    void updateMember(MemberDTO memberDTO, String nickname);
    void updateMember(MemberDTO memberDTO);
    MemberDTO login(String nickname, String pw, Boolean activeFlag);
    boolean validationCheckPw(String rawPw, String encodedPw);
    void logout();
    MemberDTO getMember(Long id);
    MemberDTO getOtherMember(Long id);
    List<MemberDTO> listMember();

    boolean nicknameValidationCheck(String nickname);

}

