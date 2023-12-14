package com.fiveguys.koguma.data.dto.club;

import lombok.Data;

@Data
public class MeetUpStateDTO {

    private Long meetUpId;
    private Long clubId;
    private Boolean isJoined;
}
