package com.fiveguys.koguma.data.entity;

import com.fiveguys.koguma.data.dto.MemberDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2083)
    private String URL;

    @Enumerated
    @Column(name = "img_type", nullable = false)
    private ImageType imageType;

    @Column(name = "rep_image_flag")
    private Boolean repImageFlag;

    @Column(name = "active_flag")
    private Boolean activeFlag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Image(Long id, String URL, ImageType imageType, Boolean repImageFlag, Boolean activeFlag, Member member, Product product, Club club, Post post) {
        this.id = id;
        this.URL = URL;
        this.imageType = imageType;
        this.repImageFlag = repImageFlag;
        this.activeFlag = activeFlag;
        this.member = member;
        this.product = product;
        this.club = club;
        this.post = post;
    }







    public void setRepImageFlag(Boolean repImageFlag) {
        this.repImageFlag = repImageFlag;
    }

}
