package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.ReviewDTO;
import com.fiveguys.koguma.repository.product.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    public void addReview() {

    }
    public ReviewDTO getReview() {
        return null;
    }
    public void deleteReview() {

    }
}
