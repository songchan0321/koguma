package com.fiveguys.koguma.data.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "member_club_join_requests")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMemberJoinRequest extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "nickname", nullable = false, length = 60)
    private String nickname;

    @Column(nullable = false, length = 60)
    private String content;

    @Column(name = "flag", nullable = false)
    private Boolean activeFlag;

    @Builder
    public ClubMemberJoinRequest(Long id, Member member, Club club, String nickname, String content,
                                 Boolean activeFlag){
        this.id = id;
        this.member = member;
        this.club = club;
        this.nickname = nickname;
        this.content = content;
        this.activeFlag = activeFlag;
    }

}
