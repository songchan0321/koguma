package com.fiveguys.koguma.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "like_filter_associations")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeFilterAssociation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public LikeFilterAssociation(Long id, Member member, Product product, Category category, Post post) {
        this.id = id;
        this.member = member;
        this.product = product;
        this.category = category;
        this.post = post;
    }
    public LikeFilterAssociation getProductAssociation(){
        return LikeFilterAssociation.builder()
                .id(id)
                .member(member)
                .product(product)
                .build();
    }
    public LikeFilterAssociation getCategoryAssociation(){
        return LikeFilterAssociation.builder()
                .id(id)
                .member(member)
                .category(category)
                .build();
    }
    public LikeFilterAssociation getPostAssociation(){
        return LikeFilterAssociation.builder()
                .id(id)
                .member(member)
                .post(post)
                .build();
    }

}
