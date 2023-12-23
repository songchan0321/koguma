package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.MemberSearchByLocationDTO;
import com.fiveguys.koguma.data.entity.Member;

import java.util.List;

public interface MemberService {


    Member addMember(MemberDTO memberDTO, String nickname, String pw, String phone, float score, String email, Boolean roleFlag, Boolean socialFlag, String profileURL);
    void deleteMember(Long id);
    MemberDTO updateMember(MemberDTO memberDTO, String nickname, String profileURL);
    void updateMember(MemberDTO memberDTO);
    MemberDTO login(String id, String pw) throws Exception;
    boolean validationCheckPw(String rawPw, String encodedPw);
    void logout();
    MemberDTO getMember(Long id);
    MemberDTO getOtherMember(Long id);
    List<MemberDTO> listMember();
    MemberDTO getMemberByEmail(String email);
    boolean nicknameValidationCheck(String nickname);

    void setScore(float culculateScore, MemberDTO memberDTO);
    List<MemberSearchByLocationDTO> searchByLocationMember(LocationDTO locationDTO,String keyword) throws Exception;

}

