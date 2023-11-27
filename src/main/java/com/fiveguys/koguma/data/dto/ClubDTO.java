package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Club;
import lombok.Data;

@Data
public class ClubDTO {

    private Long id;
    private String title;
    private String content;
    private Integer maxCapacity;

}
