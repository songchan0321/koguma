package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.data.dto.ReviewDTO;
import com.fiveguys.koguma.data.entity.Review;
import com.fiveguys.koguma.repository.product.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    public ReviewDTO addReview(ReviewDTO reviewDTO) throws Exception {

        Review review = reviewRepository.save(reviewDTO.toEntity());
        return ReviewDTO.fromEntity(review);
    }

    public ReviewDTO getReview(Long reviewId) {
        return ReviewDTO.fromEntity(reviewRepository.findById(reviewId).get());
    }

    @Override
    public String checkMemberRole(ReviewDTO reviewDTO, MemberDTO memberDTO) {
        MemberDTO buyer = reviewDTO.getProductDTO().getBuyerDTO();
        MemberDTO seller = reviewDTO.getProductDTO().getSellerDTO();
        if (memberDTO.equals(buyer)){
            return "buyer";
        }
        else if (memberDTO.equals(seller)){
            return "seller";
        }
        else
            return null;
    }

    @Override
    public boolean isPossibleAdd(String memberRole, ProductDTO productDTO) {
        Boolean check = null;
        if (memberRole.equals("buyer"))
            check =  reviewRepository.existsByProductBuyerIdAndProductIdAndSellerFlag(productDTO.getBuyerDTO().getId(),productDTO.getId(),false);
        else if (memberRole.equals("seller"))
            check =  reviewRepository.existsByProductSellerIdAndProductIdAndSellerFlag(productDTO.getSellerDTO().getId(),productDTO.getId(),true);
        return check;
    }

    @Override
    public Long getOppReviewId(MemberDTO targetDTO, ProductDTO productDTO, String targetType) {
        if ("buyer".equals(targetType)) {
            Review review = reviewRepository.findByProductBuyerIdAndProductIdAndSellerFlag(targetDTO.getId(), productDTO.getId(),false);
            return (review != null) ? review.getId() : null;
        } else if ("seller".equals(targetType)) {
            Review review = reviewRepository.findByProductSellerIdAndProductIdAndSellerFlag(targetDTO.getId(), productDTO.getId(),true);
            return (review != null) ? review.getId() : null;
        }
        return null;
    }

    @Override
    public Long getMyReviewId(MemberDTO sourceDTO, ProductDTO productDTO, String sourceType) {
        if ("buyer".equals(sourceType)) {
            Review review = reviewRepository.findByProductBuyerIdAndProductIdAndSellerFlag(sourceDTO.getId(), productDTO.getId(),false);
            return (review != null) ? review.getId() : null;
        } else if ("seller".equals(sourceType)) {
            Review review = reviewRepository.findByProductSellerIdAndProductIdAndSellerFlag(sourceDTO.getId(), productDTO.getId(),true);
            return (review != null) ? review.getId() : null;
        }
        return null;
    }

    @Override
    public float calculateScore(ReviewDTO reviewDTO) {
        float baseScore = (float) (Float.parseFloat(String.valueOf(reviewDTO.getCommet().size())) * 0.5);

        if (reviewDTO.getRating() >= 2) {
            return baseScore;
        } else {
            return -baseScore;
        }
    }


}
