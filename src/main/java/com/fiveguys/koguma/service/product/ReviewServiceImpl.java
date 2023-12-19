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

        Review review = reviewRepository.save(reviewDTO.toEntity());
        return ReviewDTO.fromEntity(review);
    }

    public ReviewDTO getReview(Long productId,boolean seller) {
        return ReviewDTO.fromEntity(reviewRepository.findByAndProductIdAndSeller(productId,seller));
    }

    @Override
    public Boolean isPossibleAdd(ReviewDTO reviewDTO) {
        return reviewRepository.existsByMemberIdAndProductIdAndSeller(reviewDTO.getMemberDTO().getId(),reviewDTO.getProductDTO().getId(),reviewDTO.isSeller());
    }


}
