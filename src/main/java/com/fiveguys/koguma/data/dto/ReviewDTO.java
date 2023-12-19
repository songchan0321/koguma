package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Review;
import com.fiveguys.koguma.data.entity.ReviewId;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor (access = AccessLevel.PROTECTED )
public class ReviewDTO {

    private Long id;
    private MemberDTO memberDTO;
    private ProductDTO productDTO;
    private int rating;
    private String content;
    private List<String> commet;
    private boolean activeFlag;
    private boolean seller;
    private LocalDateTime regDate;

    @Builder
    public ReviewDTO(Long id, MemberDTO memberDTO, ProductDTO productDTO, int rating, String content, List<String> commet, boolean activeFlag, boolean seller, LocalDateTime regDate) {
        this.id = id;
        this.memberDTO = memberDTO;
        this.productDTO = productDTO;
        this.rating = rating;
        this.content = content;
        this.commet = commet;
        this.activeFlag = activeFlag;
        this.seller = seller;
        this.regDate = regDate;
    }







    public Review toEntity() {
        return Review.builder()
                .id(id)
                .member(memberDTO.toEntity())
                .product(productDTO.toEntity())
                .rating(rating)
                .content(content)
                .commet(commet)
                .activeFlag(activeFlag)
                .seller(seller)
                .build();
    }

    public static ReviewDTO fromEntity(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .memberDTO(MemberDTO.fromEntity(review.getMember()))
                .productDTO(ProductDTO.fromEntity(review.getProduct()))
                .rating(review.getRating())
                .content(review.getContent())
                .commet(review.getCommet())
                .activeFlag(review.isActiveFlag())
                .seller(review.isSeller())
                .regDate(review.getRegDate())
                .build();
    }
}