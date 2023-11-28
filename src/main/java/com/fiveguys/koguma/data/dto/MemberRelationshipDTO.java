package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.MemberRelationship;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor

public class MemberRelationshipDTO {

    public Long id;
    public Long sourceMemberId;
    public Long targetMemberId;
    public String content;
    public boolean type;
    public LocalDateTime regDate;

    public MemberRelationshipDTO() {

    }

    public static MemberRelationshipDTO fromEntity(MemberRelationship memberRelationship){
        MemberRelationshipDTO memberRelationshipDTO = new MemberRelationshipDTO();
        memberRelationshipDTO.setId(memberRelationship.getId());
        memberRelationshipDTO.setSourceMemberId(memberRelationship.getSourceMemberId());
        memberRelationshipDTO.setTargetMemberId(memberRelationship.getTargetMemberId());
        memberRelationshipDTO.setContent(memberRelationship.getContent());
        memberRelationshipDTO.setType(memberRelationship.getType());

    }

    public MemberRelationship toEntity(){
        memberR
    }

}
