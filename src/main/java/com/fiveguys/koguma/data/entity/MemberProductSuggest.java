package com.fiveguys.koguma.data.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Table(name = "member_product_suggests")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProductSuggest extends BaseTime implements Serializable {

    @EmbeddedId
    private MemberProductSuggestId id;

    private int price;

    @Builder
    public MemberProductSuggest(MemberProductSuggestId id, int price) {
        this.id = id;
        this.price = price;
    }
}
