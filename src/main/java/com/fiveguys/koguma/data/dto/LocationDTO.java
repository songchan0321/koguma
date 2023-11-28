package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Location;
import com.fiveguys.koguma.data.entity.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
public class LocationDTO {
    private Long id;
    private MemberDTO memberDTO;
    private Double latitude;
    private Double longitude;
    private int searchRange;
    private String dong;
    private boolean repAuthLocationFlag;
    private LocalDateTime regDate;


    public Location toEntity() {
        return Location.builder()
                .id(id)
                .member(memberDTO.toEntity())
                .dong(dong)
                .latitude(latitude)
                .longitude(longitude)
                .searchRange(searchRange)
                .repAuthLocationFlag(repAuthLocationFlag)
                .build();
    }
    public static LocationDTO fromEntity(Location location){
        return LocationDTO.builder()
                .id(location.getId())
                .memberDTO(MemberDTO.fromEntity(location.getMember()))
                .dong(location.getDong())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .searchRange(location.getSearchRange())
                .repAuthLocationFlag(location.isRepAuthLocationFlag())
                .build();
    }
}
