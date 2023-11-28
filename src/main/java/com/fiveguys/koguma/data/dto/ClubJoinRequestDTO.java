package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.ClubMeetUp;
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


}
