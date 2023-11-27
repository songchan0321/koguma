package com.fiveguys.koguma.data.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location extends BaseTime{
    @javax.persistence.Id
    @Id
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @Column(nullable = false)
    private Member member;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private int search_range;

    @Column(nullable = false,length=20)
    private String dong;

    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean rep_auth_location_flag;

    public Location(Long id, Member member, Double latitude, Double longitude, int search_range, String dong, boolean rep_auth_location_flag) {
        this.id = id;
        this.member = member;
        this.latitude = latitude;
        this.longitude = longitude;
        this.search_range = search_range;
        this.dong = dong;
        this.rep_auth_location_flag = rep_auth_location_flag;
    }

}
