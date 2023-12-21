package com.fiveguys.koguma.data.dto.club;

import com.fiveguys.koguma.data.dto.ClubDTO;
import com.fiveguys.koguma.data.dto.ClubMemberDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.ClubMember;
import lombok.Builder;
import lombok.Data;

@Data
public class GetClubMemberDTO {

    private Long id;
    private ClubDTO clubDTO;
    private MemberDTO memberDTO;
    private String nickname;
    private String content;
    private Boolean memberRole;
    private Boolean activeFlag;
    private Integer count;
    private String profileURL;

    @Builder
    public GetClubMemberDTO(Long id, ClubDTO clubDTO,
                         MemberDTO memberDTO, String nickname, String content,
                         Boolean memberRole, Boolean activeFlag, String profileURL){

        this.id = id;
        this.clubDTO = clubDTO;
        this.memberDTO = memberDTO;
        this.nickname = nickname;
        this.content = content;
        this.memberRole = memberRole;
        this.activeFlag = activeFlag;
        this.profileURL = profileURL;
    }

    public GetClubMemberDTO(ClubMember clubMember){
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

    public static GetClubMemberDTO fromEntity(ClubMember entity){
        return GetClubMemberDTO.builder()
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
