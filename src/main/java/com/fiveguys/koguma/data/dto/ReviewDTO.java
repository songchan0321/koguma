package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Product;
import com.fiveguys.koguma.data.entity.Review;
import com.fiveguys.koguma.data.entity.ReviewId;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class ReviewDTO {
    private ReviewId id;
    private char rating;
    private String content;
    private String commet;
    private boolean activeFlag;
    private LocalDateTime regDate;

    @Builder
    public ReviewDTO(ReviewId id, char rating, String content, String commet, boolean activeFlag, LocalDateTime regDate) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.commet = commet;
        this.activeFlag = activeFlag;
        this.regDate = regDate;
    }




    public Review toEntity(){
        return Review.builder()
                .id(ReviewId.builder()
                        .memberId(id.getMemberId())
                        .productId(id.getProductId())
                        .build())
                .rating(rating)
                .content(content)
                .commet(commet)
                .activeFlag(activeFlag)
                .build();
    }

    public static ReviewDTO fromEntity(Review review){
        return ReviewDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .content(review.getContent())
                .commet(review.getCommet())
                .activeFlag(review.isActiveFlag())
                .commet(review.getCommet())
                .regDate(review.getRegDate())
                .build();
    }

}
