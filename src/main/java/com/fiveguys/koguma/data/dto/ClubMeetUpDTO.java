package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.ClubMeetUp;
import com.fiveguys.koguma.data.entity.MeetUpType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClubMeetUpDTO {

    private Long id;
    private ClubDTO clubDTO;
    private String title;
    private String content;
    private Integer maxCapacity;
    private LocalDateTime meetDate;
    private String roadAddr;
    private String meetUpType;

    @Builder
    public ClubMeetUpDTO(Long id, ClubDTO clubDTO, String title, String content,
                         Integer maxCapacity, LocalDateTime meetDate, String roadAddr, MeetUpType meetUpType){

        this.id = id;
        this.clubDTO = clubDTO;
        this.title = title;
        this.content = content;
        this.maxCapacity = maxCapacity;
        this.meetDate = meetDate;
        this.roadAddr = roadAddr;
        this.meetUpType = meetUpType.getName();
    }

    public ClubMeetUp toEntity(){
        return ClubMeetUp.builder()
                .id(this.id)
                .club(clubDTO.toEntity())
                .title(this.title)
                .content(this.content)
                .maxCapacity(this.maxCapacity)
                .meetDate(this.meetDate)
                .roadAddr(this.roadAddr)
                .meetUpType(MeetUpType.valueOf(this.getMeetUpType()))
                .build();
    }

    public static ClubMeetUpDTO fromEntity(ClubMeetUp entity){
        return ClubMeetUpDTO.builder()
                .id(entity.getId())
                .clubDTO(ClubDTO.fromEntity(entity.getClub()))
                .title(entity.getTitle())
                .content(entity.getContent())
                .maxCapacity(entity.getMaxCapacity())
                .meetDate(entity.getMeetDate())
                .roadAddr(entity.getRoadAddr())
                .meetUpType(MeetUpType.valueOf(entity.getMeetUpType().getName()))
                .build();
    }



}
