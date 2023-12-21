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
    private String nickname;
    private String content;
    private Boolean memberRole;
    private Boolean activeFlag;
    private Integer count;


    @Builder
    public ClubMemberDTO(Long id, ClubDTO clubDTO,
                         MemberDTO memberDTO, String nickname, String content,
                         Boolean memberRole, Boolean activeFlag){

        this.id = id;
        this.clubDTO = clubDTO;
        this.memberDTO = memberDTO;
        this.nickname = nickname;
        this.content = content;
        this.memberRole = memberRole;
        this.activeFlag = activeFlag;
    }

    public ClubMemberDTO(ClubMember clubMember){
        this.id = clubMember.getId();
        this.clubDTO = ClubDTO.fromEntity(clubMember.getClub());
        this.memberDTO = MemberDTO.fromEntity(clubMember.getMember());
        this.nickname = clubMember.getNickname();
        this.content = clubMember.getContent();
        this.memberRole = clubMember.getMemberRole();
        this.activeFlag = clubMember.getActiveFlag();
    }

    public ClubMember toEntity(){
        return ClubMember.builder()
                .id(this.id)
                .club(clubDTO.toEntity())
                .member(memberDTO.toEntity())
                .nickname(this.nickname)
                .content(this.content)
                .memberRole(this.memberRole)
                .activeFlag(this.activeFlag)
                .build();
    }

    public static ClubMemberDTO fromEntity(ClubMember entity){
        return ClubMemberDTO.builder()
                .id(entity.getId())
                .clubDTO(ClubDTO.fromEntity(entity.getClub()))
                .memberDTO(MemberDTO.fromEntity(entity.getMember()))
                .nickname(entity.getNickname())
                .content(entity.getContent())
                .memberRole(entity.getMemberRole())
                .activeFlag(entity.getActiveFlag())
                .build();
    }

}
