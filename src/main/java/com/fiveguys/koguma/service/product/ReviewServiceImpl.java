package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.ReviewDTO;
import com.fiveguys.koguma.data.entity.Review;
import com.fiveguys.koguma.data.entity.ReviewId;
import com.fiveguys.koguma.repository.product.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        Review review = reviewRepository.save(reviewDTO.toEntity());
        return ReviewDTO.fromEntity(review);
    }

    public ReviewDTO getReview(ReviewId reviewId) throws Exception {
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new Exception("상품을 찾을 수 없습니다"));
        return ReviewDTO.fromEntity(review);
    }
    public void deleteReview(ReviewId reviewId) throws Exception {
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new Exception("상품을 찾을 수 없습니다"));
        reviewRepository.delete(review);
    }
}
