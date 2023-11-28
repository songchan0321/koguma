package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Club;
import com.fiveguys.koguma.data.entity.ClubMeetUp;
import com.fiveguys.koguma.data.entity.ClubMemberJoinRequest;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClubJoinRequestDTO {

    private Long id;
    private MemberDTO memberDTO;
    private ClubDTO clubDTO;
    private String content;
    private Boolean activeFlag;
    private LocalDateTime regDate;

    @Builder
    public ClubJoinRequestDTO(Long id, MemberDTO memberDTO, ClubDTO clubDTO,
                              String content, Boolean activeFlag, LocalDateTime regDate){
        this.id = id;
        this.memberDTO = memberDTO;
        this.clubDTO = clubDTO;
        this.content = content;
        this.activeFlag = activeFlag;
        this.regDate = regDate;
    }

    public ClubMemberJoinRequest toEntity(){
        return ClubMemberJoinRequest.builder()
                .id(this.id)
                .member(memberDTO.toEntity())
                .club(clubDTO.toEntity())
                .content(this.content)
                .activeFlag(this.activeFlag)
                .build();
    }

    public static ClubJoinRequestDTO fromEntity(ClubMemberJoinRequest entity){
        return ClubJoinRequestDTO.builder()
                .id(entity.getId())
                .memberDTO(MemberDTO.fromEntity(entity.getMember()))
                .clubDTO(ClubDTO.fromEntity(entity.getClub()))
                .content(entity.getContent())
                .activeFlag(entity.getActiveFlag())
                .regDate(entity.getRegDate())
                .build();
    }


}
