package com.fiveguys.koguma.data.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "club_member_meet_up_joins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMemberMeetUpJoin extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meet_up_id", nullable = false)
    private ClubMeetUp clubMeetUp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id", nullable = false)
    private ClubMember clubMember;

    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag;

    @Builder
    public ClubMemberMeetUpJoin(Long id, ClubMeetUp clubMeetUp, ClubMember clubMember,
                                Boolean activeFlag){
        this.id = id;
        this.clubMeetUp = clubMeetUp;
        this.clubMember = clubMember;
        this.activeFlag = activeFlag;
    }
}
