package com.fiveguys.koguma.data.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTime implements Serializable{


    @EmbeddedId
    private ReviewId id;

    @Column(nullable = false)
    private char rating;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String commet;

    @Column(name = "active_flag",nullable = false)
    private boolean activeFlag;

    @Builder
    public Review(ReviewId id, char rating, String content, String commet, boolean activeFlag) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.commet = commet;
        this.activeFlag = activeFlag;
    }
}
