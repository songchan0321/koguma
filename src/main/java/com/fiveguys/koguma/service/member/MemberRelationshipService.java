package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.MemberRelationshipType;

import java.util.List;

public interface MemberRelationshipService {

    void addBlock(MemberRelationshipDTO memberRelationshipDTO);
    void deleteBlock(MemberRelationshipDTO memberRelationshipDTO);
    List<MemberRelationshipDTO> listBlock(Long sourceMemberId);
    MemberRelationshipDTO getBlock(Long sourceMember);
    void addFollowing(MemberRelationshipDTO memberRelationshipDTO);
    void deleteFollowing(Long id);
    List<MemberRelationshipDTO> listFollowing(Long sourceMemberId);
    MemberRelationshipDTO getFollowing(Long sourceMember);


}
