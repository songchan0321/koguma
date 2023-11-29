package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.ClubMemberMeetUpJoin;
import lombok.Builder;
import lombok.Data;

@Data
public class ClubMemberMeetUpJoinDTO {

    private Long id;
    private ClubMeetUpDTO clubMeetUpDTO;
    private ClubMemberDTO clubMemberDTO;
    private Boolean activeFlag;

    @Builder
    public ClubMemberMeetUpJoinDTO(Long id, ClubMeetUpDTO clubMeetUpDTO,
                                   ClubMemberDTO clubMemberDTO, Boolean activeFlag){
        this.id = id;
        this.clubMeetUpDTO = clubMeetUpDTO;
        this.clubMemberDTO = clubMemberDTO;
        this.activeFlag = activeFlag;
    }

    public ClubMemberMeetUpJoin toEntity(){
        return ClubMemberMeetUpJoin.builder()
                .id(this.id)
                .clubMeetUp(clubMeetUpDTO.toEntity())
                .clubMember(clubMemberDTO.toEntity())
                .activeFlag(this.activeFlag)
                .build();
    }

    public static ClubMemberMeetUpJoinDTO fromEntity(ClubMemberMeetUpJoin entity){
        return ClubMemberMeetUpJoinDTO.builder()
                .id(entity.getId())
                .clubMeetUpDTO(ClubMeetUpDTO.fromEntity(entity.getClubMeetUp()))
                .clubMemberDTO(ClubMemberDTO.fromEntity(entity.getClubMember()))
                .activeFlag(entity.getActiveFlag())
                .build();
    }

}
