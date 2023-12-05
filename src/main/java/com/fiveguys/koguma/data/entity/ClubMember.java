package com.fiveguys.koguma.data.entity;

import com.fiveguys.koguma.data.dto.ClubMemberDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "club_members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
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
    private String nickname;

    @Column(nullable = false, length = 60)
    private String content;

    @Column(name = "member_role", nullable = false)
    private Boolean memberRole;

    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag;


    @Builder
    public ClubMember(Long id, Club club, Member member, String nickname, String content,
                      Boolean memberRole, Boolean activeFlag){
        this.id = id;
        this.club = club;
        this.member = member;
        this.nickname = nickname;
        this.content = content;
        this.memberRole = memberRole;
        this.activeFlag = activeFlag;
    }

   public void updateClubMemberRole(){
        this.memberRole = true;
    }

   public void updateClubMember(String nickname, String content){
        this.nickname = nickname;
        this.content = content;
   }

   /*
   * 모임원 생성 메서드
   * */
   public static ClubMember createClubMember(Club club, Member member, String nickname, String content){
       return ClubMember.builder()
                .club(club)
                .member(member)
                .nickname(nickname)
                .content(content)
                .memberRole(false)
                .activeFlag(true)
                .build();
   }

   /*
   * 모임장으로 승격 메서드 (모임원 -> 모임장)
   * */
    public void promoteClubLeader(){
        if (this.memberRole){
            throw new IllegalStateException("모임원이 아니고, 모임장입니다.");
        }
        this.memberRole = true;
    }

    /*
     * 모임원으로 강등 메서드 (모임장 -> 모임원)
     * */
    public void demoteClubMember(){
        if (!this.memberRole){
            throw new IllegalStateException("모임장이 아니고 모임원입니다.");
        }
        this.memberRole = false;
    }

}
