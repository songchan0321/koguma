package com.fiveguys.koguma.data.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Table(name = "meet_up")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMeetUp extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Column(nullable = false, length = 72)
    private String title;

    @Column(nullable = false, length = 900)
    private String content;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @Column(name = "meet_date", nullable = false)
    private LocalDateTime meetDate;

    @Column(name = "road_addr", nullable = false, length = 90)
    private String roadAddr;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_flag", nullable = false)
    private  MeetUpType meetUpType;

    @Builder
    public ClubMeetUp(Long id, Club club, String title, String content,
                      Integer maxCapacity, LocalDateTime meetDate, String roadAddr, MeetUpType meetUpType){
        this.id = id;
        this.club = club;
        this.title = title;
        this.content = content;
        this.maxCapacity = maxCapacity;
        this.meetDate = meetDate;
        this.roadAddr = roadAddr;
        this.meetUpType = meetUpType;
    }

    public static ClubMeetUp createClubMeetUp(Club club, String title, String content, Integer maxCapacity, MeetUpType meetUpType, String roadAddr, LocalDateTime meetDate){
        return ClubMeetUp.builder()
                .club(club)
                .title(title)
                .content(content)
                .maxCapacity(maxCapacity)
                .meetUpType(meetUpType)
                .roadAddr(roadAddr)
                .meetDate(meetDate)
                .build();
    }

    public void updateClubMeetUp(String title, String content, Integer maxCapacity,
                                 LocalDateTime meetDate, String roadAddr){
        this.title = title;
        this.content = content;
        this.maxCapacity = maxCapacity;
        this.meetDate = meetDate;
        this.roadAddr = roadAddr;
    }

    public void changeActiveFlag(){

        this.meetUpType = MeetUpType.COMPLETE;
    }

    public void onPrePersist() {
        String customLocalDateTimeFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime parsedCreateDate = LocalDateTime.parse(customLocalDateTimeFormat, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.meetDate = parsedCreateDate;
    }

}
