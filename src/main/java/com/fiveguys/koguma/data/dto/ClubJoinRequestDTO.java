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
    private String nickname;
    private String content;
    private Boolean activeFlag;
    private LocalDateTime regDate;

    @Builder
    public ClubJoinRequestDTO(Long id, MemberDTO memberDTO, ClubDTO clubDTO, String nickname,
                              String content, Boolean activeFlag, LocalDateTime regDate){
        this.id = id;
        this.memberDTO = memberDTO;
        this.clubDTO = clubDTO;
        this.nickname = nickname;
        this.content = content;
        this.activeFlag = activeFlag;
        this.regDate = regDate;
    }

    public ClubJoinRequestDTO(ClubMemberJoinRequest clubMemberJoinRequest){
        this.id = clubMemberJoinRequest.getId();
        this.memberDTO = MemberDTO.fromEntity(clubMemberJoinRequest.getMember());
        this.clubDTO = ClubDTO.fromEntity(clubMemberJoinRequest.getClub());
        this.nickname = clubMemberJoinRequest.getNickname();
        this.content = clubMemberJoinRequest.getContent();
        this.activeFlag = clubMemberJoinRequest.getActiveFlag();
        this.regDate = clubMemberJoinRequest.getRegDate();
    }

    public ClubMemberJoinRequest toEntity(){
        return ClubMemberJoinRequest.builder()
                .id(this.id)
                .member(memberDTO.toEntity())
                .club(clubDTO.toEntity())
                .nickname(this.nickname)
                .content(this.content)
                .activeFlag(this.activeFlag)
                .build();
    }

    public static ClubJoinRequestDTO fromEntity(ClubMemberJoinRequest entity){
        return ClubJoinRequestDTO.builder()
                .id(entity.getId())
                .memberDTO(MemberDTO.fromEntity(entity.getMember()))
                .clubDTO(ClubDTO.fromEntity(entity.getClub()))
                .nickname(entity.getNickname())
                .content(entity.getContent())
                .activeFlag(entity.getActiveFlag())
                .regDate(entity.getRegDate())
                .build();
    }


}
