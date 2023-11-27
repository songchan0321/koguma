package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Member;

public interface MemberService {

    void addMember(MemberDTO memberDTO);

    void deleteMember(Long id);

    void updateMember(Long id);
    MemberDTO login(MemberDTO memberDTO);

    public void logout();

    MemberDTO getMember(Long id);

    MemberDTO getOtherMember(String nickname);
    MemberDTO listMember(Long id);




}