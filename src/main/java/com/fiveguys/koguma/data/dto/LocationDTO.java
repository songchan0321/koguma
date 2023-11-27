package com.fiveguys.koguma.data.dto;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import java.lang.reflect.Member;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    private Long id;
    private Long member_id;
    private Double latitude;
    private Double longitude;
    private int search_range;
    private String dong;
    private boolean rep_auth_location_flag;
    private LocalDateTime regDate;
}
