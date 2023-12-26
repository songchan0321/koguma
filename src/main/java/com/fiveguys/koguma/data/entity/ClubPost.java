package com.fiveguys.koguma.data.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Table(name="club_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class ClubPost extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Column(name = "club_name")
    private String clubName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="club_id")
    private Club club;

    @Column(name = "club_member_nickname")
    private String clubMemberNickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_post_category_id")
    private ClubPostCategory clubPostCategory;

    @Column(name = "club_post_category_name")
    private String categoryName;

    private String images;
    private int views;

    @Builder
    public ClubPost(Long id, String title, String content, Club club, String clubName,
                    Member member, ClubPostCategory clubPostCategory, String categoryName,
                    String images, String clubMemberNickname){
        this.id = id;
        this.title = title;
        this.content=content;
        this.club=club;
        this.clubName=clubName;
        this.member = member;
        this.clubPostCategory = clubPostCategory;
        this.categoryName = categoryName;
        this.clubMemberNickname = clubMemberNickname;
        this.images = images;
    }

    public static ClubPost createClubPost(String title, String content, Club club, String clubName, Member member, ClubPostCategory clubPostCategory,
                                          String categoryName, String clubMemberNickname, String images){
        return ClubPost.builder()
                .title(title)
                .content(content)
                .club(club)
                .clubName(clubName)
                .member(member)
                .clubPostCategory(clubPostCategory)
                .categoryName(categoryName)
                .clubMemberNickname(clubMemberNickname)
                .images(images)
                .build();
    }

    public void increaseViews(){
        this.views++;
    }
}
