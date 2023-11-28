package com.fiveguys.koguma.data.entity;

import lombok.AccessLevel;
import lombok.Builder;
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
    @JoinColumn(name = "club_id", nullable = false)
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

    @Builder
    public ClubMeetUp(Long id, Club club, String title, String content,
                      Integer maxCapacity, LocalDateTime meetDate, String roadAddr, Boolean activeFlag){
        this.id = id;
        this.club = club;
        this.title = title;
        this.content = content;
        this.maxCapacity = maxCapacity;
        this.meetDate = meetDate;
        this.roadAddr = roadAddr;
        this.activeFlag = activeFlag;
    }


}
