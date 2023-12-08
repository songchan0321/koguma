package com.fiveguys.koguma.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.MemberRelationship;
import com.fiveguys.koguma.data.entity.MemberRelationshipType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor

public class MemberRelationshipDTO  {

    public Long id;

    public String content;
    public boolean type;
    public LocalDateTime regDate;
    public MemberRelationshipType memberRelationshipType;
    public Member sourceMember;
    public Member targetMember;
    public MemberRelationshipDTO() {

    }


    public static MemberRelationshipDTO fromEntity(MemberRelationship memberRelationship){
        MemberRelationshipDTO memberRelationshipDTO = new MemberRelationshipDTO();
        memberRelationshipDTO.setId(memberRelationship.getId());
        memberRelationshipDTO.setSourceMember(memberRelationship.getSourceMember());
        memberRelationshipDTO.setTargetMember(memberRelationship.getTargetMember());
        memberRelationshipDTO.setContent(memberRelationship.getContent());
        memberRelationshipDTO.setMemberRelationshipType(memberRelationship.getMemberRelationshipType());
        return memberRelationshipDTO;

    }

    public MemberRelationship toEntity(){
        MemberRelationship memberRelationship = new MemberRelationship();
        memberRelationship.setId(id);
        memberRelationship.setSourceMember(sourceMember);
        memberRelationship.setTargetMember(targetMember);
        memberRelationship.setContent(content);
        memberRelationship.setMemberRelationshipType(memberRelationshipType);
        return memberRelationship;
    }

}
