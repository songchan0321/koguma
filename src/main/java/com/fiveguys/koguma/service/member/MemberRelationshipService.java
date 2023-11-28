package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
public interface MemberRelationshipService {

    void addBlock(MemberRelationshipDTO memberRelationshipDTO, Long sourceMemberId, Long targetMemberId, String content);

    void deleteBlock(MemberRelationshipDTO memberRelationshipDTO);
    void listBlock(MemberRelationshipDTO memberRelationshipDTO);
    public void getBlock(Long id);
    void addFollowing(MemberRelationshipDTO memberRelationshipDTO);
    void deleteFollowing(Long id, String nickname);
    void listFollowing(Long id, String nickname);
}
