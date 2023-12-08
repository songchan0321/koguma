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
    private ClubDTO clubDTO;
    private String nickname;
    private String content;
    private Boolean activeFlag;
    private LocalDateTime regDate;

    @Builder
    public ClubJoinRequestDTO(Long id,ClubDTO clubDTO, String nickname,
                              String content, Boolean activeFlag, LocalDateTime regDate){
        this.id = id;
        this.clubDTO = clubDTO;
        this.nickname = nickname;
        this.content = content;
        this.activeFlag = activeFlag;
        this.regDate = regDate;
    }

    public ClubJoinRequestDTO(ClubMemberJoinRequest clubMemberJoinRequest){
        this.id = clubMemberJoinRequest.getId();
        this.clubDTO = ClubDTO.fromEntity(clubMemberJoinRequest.getClub());
        this.nickname = clubMemberJoinRequest.getNickname();
        this.content = clubMemberJoinRequest.getContent();
        this.activeFlag = clubMemberJoinRequest.getActiveFlag();
        this.regDate = clubMemberJoinRequest.getRegDate();
    }

    public ClubMemberJoinRequest toEntity(){
        return ClubMemberJoinRequest.builder()
                .id(this.id)
                .club(clubDTO.toEntity())
                .nickname(this.nickname)
                .content(this.content)
                .activeFlag(this.activeFlag)
                .build();
    }

    public static ClubJoinRequestDTO fromEntity(ClubMemberJoinRequest entity){
        return ClubJoinRequestDTO.builder()
                .id(entity.getId())
                .clubDTO(ClubDTO.fromEntity(entity.getClub()))
                .nickname(entity.getNickname())
                .content(entity.getContent())
                .activeFlag(entity.getActiveFlag())
                .regDate(entity.getRegDate())
                .build();
    }


}
