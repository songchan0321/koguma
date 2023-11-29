package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.ReviewDTO;

public interface ReviewService {

    void addReview();
    ReviewDTO getReview();
    void deleteReview();


}
