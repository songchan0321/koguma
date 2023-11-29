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
    @JoinColumn(name="products",nullable = false)
    private Product product_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="members",nullable = false)
    private Member member_id;

    @Builder
    public MemberProductSuggestId(Product product_id, Member member_id) {
        this.product_id = product_id;
        this.member_id = member_id;
    }
}
