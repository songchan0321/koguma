package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.entity.Member;

import java.util.List;

public interface MemberRelationshipService {

    void addBlock(MemberRelationshipDTO memberRelationshipDTO, Member sourceMemberId, Member targetMemberId, String content);
    void deleteBlock(MemberRelationshipDTO memberRelationshipDTO);
    List<MemberRelationshipDTO> listBlock();
    MemberRelationshipDTO getBlock(Long id);
    void addFollowing(MemberRelationshipDTO memberRelationshipDTO, Member sourceMemberId, Member targetMemberId);
    void deleteFollowing(MemberRelationshipDTO memberRelationshipDTO);
    List<MemberRelationshipDTO> listFollowing();
}
