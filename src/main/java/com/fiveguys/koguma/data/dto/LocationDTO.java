package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Location;
import com.fiveguys.koguma.data.entity.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    private Long id;
    private Long memberId;
    private Double latitude;
    private Double longitude;
    private int searchRange;
    private String dong;
    private boolean repAuthLocationFlag;
    private LocalDateTime regDate;

    public Location toEntity() {
        return Location.builder()
                .id(id)
                .member(Member.builder().build())
                .dong(dong)
                .latitude(latitude)
                .longitude(longitude)
                .searchRange(searchRange)
                .repAuthLocationFlag(repAuthLocationFlag)
                .build();
    }
}
