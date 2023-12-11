package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.entity.MemberRelationshipType;

import java.util.List;

public interface MemberRelationshipService {

    void addBlock(MemberRelationshipDTO memberRelationshipDTO);
    void deleteBlock(Long sourceMember, Long targetMember);
    List<MemberRelationshipDTO> listBlock(Long sourceMemberId);
    MemberRelationshipDTO getBlock(Long sourceMember);
    void addFollowing(MemberRelationshipDTO memberRelationshipDTO);
    void deleteFollowing(Long sourceMember, Long targetMember);
    List<MemberRelationshipDTO> listFollowing(Long sourceMemberId);
    MemberRelationshipDTO getFollowing(Long sourceMember);

}
