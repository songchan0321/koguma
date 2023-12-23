package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.data.dto.ReviewDTO;
import com.fiveguys.koguma.data.entity.Review;
import com.fiveguys.koguma.data.entity.ReviewId;
import org.springframework.data.domain.Page;

public interface ReviewService {

    ReviewDTO addReview(ReviewDTO reviewDTO) throws Exception;
    ReviewDTO getReview(Long reviewId);
    void deleteReview(Long productId);
    String checkMemberRole(ReviewDTO reviewDTO, MemberDTO memberDTO);

    boolean isPossibleAdd(String memberRole, ProductDTO productDTO);
    Long getOppReviewId(MemberDTO targetDTO,ProductDTO productDTO,String targetType);
    Long getMyReviewId(MemberDTO sourceDTO, ProductDTO productDTO, String sourceType);

    float calculateScore(ReviewDTO reviewDTO);
    Boolean isProductHaveReview(Long productId);


}
