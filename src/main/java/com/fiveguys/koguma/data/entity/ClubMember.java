package com.fiveguys.koguma.data.entity;

import com.fiveguys.koguma.data.dto.ClubMemberDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "club_members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMember extends BaseTime{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = 60)
    private String nickName;

    @Column(nullable = false)
    private boolean memberRole;

    @Column(nullable = false)
    private boolean activeFlag;


}
