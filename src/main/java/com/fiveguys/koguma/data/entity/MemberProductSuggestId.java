package com.fiveguys.koguma.data.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class MemberProductSuggestId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id",nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id",nullable = false)
    private Member member;

    @Builder
    public MemberProductSuggestId(Product product, Member member) {
        this.product = product;
        this.member = member;
    }
}
