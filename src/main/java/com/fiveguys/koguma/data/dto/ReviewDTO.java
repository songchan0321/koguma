package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Review;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor (access = AccessLevel.PROTECTED )
public class ReviewDTO {

    private Long id;
    private ProductDTO productDTO;
    private int rating;
    private String content;
    private List<String> commet;
    private boolean sellerFlag;
    private boolean activeFlag;
    private LocalDateTime regDate;

    @Builder
    public ReviewDTO(Long id, ProductDTO productDTO, int rating, String content, List<String> commet, boolean sellerFlag, boolean activeFlag, LocalDateTime regDate) {
        this.id = id;
        this.productDTO = productDTO;
        this.rating = rating;
        this.content = content;
        this.commet = commet;
        this.sellerFlag = sellerFlag;
        this.activeFlag = activeFlag;
        this.regDate = regDate;
    }



    public Review toEntity() {
        return Review.builder()
                .id(id)
                .product(productDTO.toEntity())
                .rating(rating)
                .content(content)
                .commet(commet)
                .sellerFlag(sellerFlag)
                .activeFlag(activeFlag)
                .build();
    }

    public static ReviewDTO fromEntity(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .productDTO(ProductDTO.fromEntity(review.getProduct()))
                .rating(review.getRating())
                .content(review.getContent())
                .commet(review.getCommet())
                .sellerFlag(review.isSellerFlag())
                .activeFlag(review.isActiveFlag())
                .regDate(review.getRegDate())
                .build();
    }
}