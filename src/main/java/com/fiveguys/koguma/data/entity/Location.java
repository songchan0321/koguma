package com.fiveguys.koguma.data.entity;

import com.fiveguys.koguma.data.dto.LocationDTO;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location extends BaseTime{


    @Id
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @Column(nullable = false)
    private Member member;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private int searchRange;

    @Column(nullable = false,length=20)
    private String dong;

    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean repAuthLocationFlag;

    public Location(Long id, Member member, Double latitude, Double longitude, int searchRange, String dong, boolean repAuthLocationFlag) {
        this.id = id;
        this.member = member;
        this.latitude = latitude;
        this.longitude = longitude;
        this.searchRange = searchRange;
        this.dong = dong;
        this.repAuthLocationFlag = repAuthLocationFlag;
    }

    public LocationDTO toDTO(){
        return LocationDTO.builder()
                .id(id)
                .memberId(member.getId())
                .dong(dong)
                .latitude(latitude)
                .longitude(longitude)
                .searchRange(searchRange)
                .repAuthLocationFlag(repAuthLocationFlag)
                .build();
    }

    public void setSearchRange(int newSearchRange) {
        this.searchRange = newSearchRange;
    }
    public void setRepAuthLocationFlag(Boolean state){
        this.repAuthLocationFlag = state;
    }
}
