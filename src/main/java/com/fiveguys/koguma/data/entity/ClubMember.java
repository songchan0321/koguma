package com.fiveguys.koguma.data.entity;

import com.fiveguys.koguma.data.dto.ClubMemberDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "club_members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMember extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "nickname", nullable = false, length = 60)
    private String nickName;

    @Column(nullable = false)
    private String content;

    @Column(name = "member_role", nullable = false)
    private Boolean memberRole;

    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag;


    @Builder
    public ClubMember(Long id, Club club, Member member, String nickName, String content, Boolean memberRole){
        this.id = id;
        this.club = club;
        this.member = member;
        this.nickName = nickName;
        this.content = content;
        this.memberRole = memberRole;
    }

   public void updateClubMemberRole(){
        this.memberRole = false;
    }

   public void updateClubMember(String nickName, String content){
        this.nickName = nickName;
        this.content = content;
   }

}
