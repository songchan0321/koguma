package com.fiveguys.koguma.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "club_comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubComment extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_post_id")
    private ClubPost clubPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id")
    private ClubMember clubMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    private String content;

    @Builder
    public ClubComment(Long id, ClubPost clubPost, Club club, ClubMember clubMember,String content){

        this.id = id;
        this.clubPost = clubPost;
        this.club =club;
        this.content = content;
        this.clubMember = clubMember;
    }

}
