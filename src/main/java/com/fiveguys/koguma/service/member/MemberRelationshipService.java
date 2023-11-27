package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
public interface MemberRelationshipService {

    void addBlock(MemberRelationshipDTO memberRelationshipDTO);
    void deleteBlock(Long id, String nickname);
    MemberRelationshipDTO listBlock(Long id);
    MemberRelationshipDTO getBlock(Long id);
    void addFollowing(MemberRelationshipDTO memberRelationshipDTO);
    void deleteFollowing(Long id, String nickname);
    MemberRelationshipDTO listFollowing(Long id, String nickname);
}
