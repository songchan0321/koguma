package com.fiveguys.koguma.data.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "club_meet_ups")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMeetUp extends BaseTime{

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "club_id", nullable = false)
    private Club club;

    @Column(nullable = false, length = 72)
    private String title;

    @Column(nullable = false, length = 900)
    private String content;

    @Column(nullable = false)
    private Integer maxCapacity;

    @Column(nullable = false)
    private LocalDateTime meetDate;

    @Column(nullable = false, length = 90)
    private String roadAddr;

    @Column(nullable = false)
    private Boolean activeFlag;

}
