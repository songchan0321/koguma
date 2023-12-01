package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.ReviewDTO;
import com.fiveguys.koguma.data.entity.ReviewId;

public interface ReviewService {

    ReviewDTO addReview(ReviewDTO reviewDTO);
    ReviewDTO getReview(ReviewId reviewId) throws Exception;
    void deleteReview(ReviewId reviewId) throws Exception;


}
