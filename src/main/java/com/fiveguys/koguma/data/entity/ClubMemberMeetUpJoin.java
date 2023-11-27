package com.fiveguys.koguma.data.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "club_member_meet_up_joins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMemberMeetUpJoin extends BaseTime{

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_meet_up_id", nullable = false)
    private ClubMeetUp clubMeetUp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Boolean activeFlag;
}
