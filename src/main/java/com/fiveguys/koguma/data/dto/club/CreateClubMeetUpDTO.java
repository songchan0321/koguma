package com.fiveguys.koguma.data.dto.club;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateClubMeetUpDTO {

    private Long clubId;
    private String title;
    private String content;
    private Integer maxCapacity;
    private String roadAddr;
    private LocalDateTime meetData;

}
