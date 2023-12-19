package com.fiveguys.koguma.data.entity;

import com.fiveguys.koguma.data.dto.ReviewDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTime {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private String content;

    @ElementCollection
    @CollectionTable(name = "commet", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "commet", nullable = false)
    private List<String> commet = new ArrayList<>();

    @Column(name = "active_flag", nullable = false)
    private boolean activeFlag;
    @Column(name = "seller", nullable = false)
    private boolean seller;



    @Builder
    public Review(Long id, Member member, Product product, int rating, String content, List<String> commet, boolean activeFlag, boolean seller) {
        this.id = id;
        this.member = member;
        this.product = product;
        this.rating = rating;
        this.content = content;
        this.commet = commet;
        this.activeFlag = activeFlag;
        this.seller = seller;
    }
}
