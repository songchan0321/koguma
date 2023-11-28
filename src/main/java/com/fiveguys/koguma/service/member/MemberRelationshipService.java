package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.entity.Member;

public interface MemberRelationshipService {

    void addBlock(MemberRelationshipDTO memberRelationshipDTO, Member sourceMemberId, Member targetMemberId, String content);

    void deleteBlock(MemberRelationshipDTO memberRelationshipDTO);
    void listBlock(MemberRelationshipDTO memberRelationshipDTO);
    public void getBlock(Long id);
    void addFollowing(MemberRelationshipDTO memberRelationshipDTO);
    void deleteFollowing(Long id, String nickname);
    void listFollowing(Long id, String nickname);
}
