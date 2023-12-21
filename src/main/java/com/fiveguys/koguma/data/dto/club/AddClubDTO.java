package com.fiveguys.koguma.data.dto.club;

import com.fiveguys.koguma.data.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddClubDTO {

    private String title;
    private String content;
    private String nickname;
    private Integer maxCapacity;
    private Double latitude;
    private Double longitude;
    private String dong;
    private Long categoryId;


}
