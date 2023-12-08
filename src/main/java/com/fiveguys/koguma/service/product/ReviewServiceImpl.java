package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ReviewDTO;
import com.fiveguys.koguma.data.entity.Review;
import com.fiveguys.koguma.data.entity.ReviewId;
import com.fiveguys.koguma.repository.product.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    public ReviewDTO addReview(ReviewDTO reviewDTO) throws Exception {
        if (getReview(reviewDTO.getId().getProduct().getId()) != null) {
            throw new Exception("이미 리뷰를 작성했습니다");
        }

        Review review = reviewRepository.save(reviewDTO.toEntity());
        return ReviewDTO.fromEntity(review);
    }

    public ReviewDTO getReview(Long productId) {
        return reviewRepository.findByIdProductId(productId)
                .map(ReviewDTO::fromEntity)
                .orElse(null);
    }



    public void deleteReview(ReviewId reviewId) throws Exception {
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new Exception("상품을 찾을 수 없습니다"));
        reviewRepository.delete(review);
    }

    public Page<Review> listReview(MemberDTO memberDTO,int page) {
        Pageable pageable = PageRequest.of(page,9);
        return reviewRepository.findAllByIdProductSellerId(memberDTO.getId(),pageable);
    }

}
