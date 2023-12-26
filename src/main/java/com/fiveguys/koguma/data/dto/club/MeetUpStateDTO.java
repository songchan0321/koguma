package com.fiveguys.koguma.data.dto.club;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetUpStateDTO {

    private Long meetUpId;
    private Long clubId;
    private Boolean isJoined;
}
