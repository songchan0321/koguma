package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.ClubMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class ClubMemberDTO {

    private Long id;
    private ClubDTO clubDTO;
    private MemberDTO memberDTO;
    private String nickName;
    private Boolean memberRole;


    @Builder
    public ClubMemberDTO(Long id, ClubDTO clubDTO,
                         MemberDTO memberDTO, String nickName, Boolean memberRole){

        this.id = id;
        this.clubDTO = clubDTO;
        this.memberDTO = memberDTO;
        this.nickName = nickName;
        this.memberRole = memberRole;
    }

    public ClubMember toEntity(){
        return ClubMember.builder()
                .id(this.id)
                .club(clubDTO.toEntity())
                //.member(memberDTO.toEntity())
                .build();
    }

    public static ClubMemberDTO fromEntity(ClubMember entity){
        return ClubMemberDTO.builder()
                .id(entity.getId())
                .clubDTO(ClubDTO.fromEntity(entity.getClub()))
                //.memberDTO
                .nickName(entity.getNickName())
                .memberRole(entity.getMemberRole())
                .build();
    }

}
